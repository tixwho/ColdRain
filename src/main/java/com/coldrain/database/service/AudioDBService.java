package com.coldrain.database.service;

import com.coldrain.database.bo.AlbumBo;
import com.coldrain.database.bo.ArtistBo;
import com.coldrain.database.bo.FileBo;
import com.coldrain.database.bo.MetaBo;
import com.coldrain.database.bo.SongBo;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AudioDBService")
public class AudioDBService {

    private final static Logger logger = LoggerFactory.getLogger(AudioDBService.class);

    final
    AlbumBo albumBo;
    final
    ArtistBo artistBo;
    final
    FileBo fileBo;
    final
    MetaBo metaBo;
    final
    SongBo songBo;

    @Autowired
    public AudioDBService(AlbumBo albumBo, ArtistBo artistBo, FileBo fileBo, MetaBo metaBo,
        SongBo songBo) {
        this.albumBo = albumBo;
        this.artistBo = artistBo;
        this.fileBo = fileBo;
        this.metaBo = metaBo;
        this.songBo = songBo;
    }


    public FileModel scanSingle(MetaSong metaSong){
        //file prototypeT");
        FileModel fileM = fileBo.findBySrc(metaSong.getSrc());
        if(fileM==null){
            fileM=fileBo.createFileModel(metaSong);
        }
        //find
        //start with artist
        ArtistModel artistM = artistBo.guaranteeArtistModel_track(metaSong);
        ArtistModel albumArtistM = artistBo.guaranteeArtistModel_album(metaSong);
        //songM, always add to relation
        SongModel songM = songBo.findByTitleAndArtistM(metaSong.getTrackTitle(),artistM);
        if(songM==null) {
            songM = songBo.guaranteeSongModel(metaSong.getTrackTitle(),artistM);
            artistBo.registerSongMtoArtistM(artistM, songM);
        }
        //AlbumM, always add to relation
        AlbumModel albumM=albumBo.findByAlbumAndArtistM(metaSong.getAlbum(),albumArtistM);
        if(albumM==null){
            albumM = albumBo.guaranteeAlbumModel(metaSong,albumArtistM);
            artistBo.registerAlbumMtoArtistM(albumArtistM, albumM);
        }
        System.out.println(albumM);
        System.out.println(albumArtistM);

        //End with Meta, always add to relation
        MetaModel metaM = metaBo.findByAlbumMandSongM(albumM,songM);
        if(metaM==null) {
            metaM = metaBo.guaranteeMetaModel(metaSong, albumM, songM);
            albumBo.registerMetaMtoAlbumM(albumM, metaM);
            songBo.registerMetaMtoSongM(songM, metaM);
        }
        //link with file
        fileBo.attachMetaMToFileM(fileM,metaM);
        metaBo.registerFileMtoMetaM(metaM,fileM);
        logger.debug("Meta loaded:"+metaM);
        return fileM;
    }

    /**
     * Not responsible for checking file existence or record deletion.
     * Requires n+1 load(fileMs in metaM). Use with caution
     * @param fileM FileModel to be disposed & unlinked with Meta
     */
    public void disposeSingle(FileModel fileM){
        fileM.getMetaM().getFileModels().remove(fileM);
        fileBo.delete(fileM);
    }

    /**
     * Delete invalid FileMs and detach MetaM. Not responsible for MetaM cleanse.
     */
    public void invalidFileMFullCleanse(){
        List<FileModel> invalidFileMs = fileBo.findAllInvalidFiles();
        for(FileModel invalidFileM: invalidFileMs){
            disposeSingle(invalidFileM);
            logger.debug("Disposed invalid fileM"+invalidFileM);
        }
    }

    /**
     * Cleanse ALL unused MetaM, AlbumM, ArtistM, and SongM
     * Uses 4 separate checks from Bo layer
     */
    public void invalidMetaCleanup(){
        //meta relationship deletion
        List<MetaModel> unusedMetaMs = metaBo.findUnusedMetaM();
        for(MetaModel unusedMetaM: unusedMetaMs){
            unusedMetaM.getAlbumM().getMetaModels().remove(unusedMetaM);
            unusedMetaM.getSongM().getMetaModels().remove(unusedMetaM);
            metaBo.delete(unusedMetaM);
            logger.debug("Deleted MetaM:"+unusedMetaM);
        }
        //Album relationship deletion
        List<AlbumModel> unusedAlbumMs = albumBo.findUnusedAlbumMs();
        for(AlbumModel unusedAlbumM: unusedAlbumMs){
            unusedAlbumM.getAlbumArtistM().getAlbumModels().remove(unusedAlbumM);
            albumBo.delete(unusedAlbumM);
            logger.debug("Deleted AlbumM:"+unusedAlbumM);
        }
        //Song relationship deletion
        List<SongModel> unusedSongMs = songBo.findUnusedSongMs();
        for(SongModel unusedSongM: unusedSongMs){
            unusedSongM.getArtistM().getSongModels().remove(unusedSongM);
            songBo.delete(unusedSongM);
            logger.debug("Deleted SongM:"+unusedSongM);
        }
        //Artist relationship deletion
        List<ArtistModel> unusedArtistMs = artistBo.findUnusedArtistMs();
        for(ArtistModel unusedArtistM: unusedArtistMs){
            //last chain of relationship, no relationship deletion needed
            artistBo.delete(unusedArtistM);
            logger.debug("Deleted ArtistM:"+unusedArtistM);
        }
    }

}
