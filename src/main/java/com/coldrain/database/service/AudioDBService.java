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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AudioDBService")
public class AudioDBService {

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


    public MetaModel scanSingle(MetaSong metaSong){
        //file prototype
        System.out.println("FINDBYSRC START");
        FileModel fileM = fileBo.findBySrc(metaSong.getSrc());
        if(fileM==null){
            fileM=fileBo.createFileModel(metaSong);
        }
        //find
        //start with artist
        System.out.println(("ARTIST SCAN START"));
        ArtistModel artistM = artistBo.guaranteeArtistModel_track(metaSong);
        ArtistModel albumArtistM = artistBo.guaranteeArtistModel_album(metaSong);
        //songM, always add to relation
        System.out.println("SONG SCAN START");
        SongModel songM = songBo.findByTitleAndArtistM(metaSong.getTrackTitle(),artistM);
        if(songM==null) {
            songM = songBo.guaranteeSongModel(metaSong.getTrackTitle(),artistM);
            artistBo.registerSongMtoArtistM(artistM, songM);
        }
        //AlbumM, always add to relation
        System.out.println("ALBUM SCAN START");
        AlbumModel albumM=albumBo.findByAlbumAndArtistM(metaSong.getAlbum(),albumArtistM);
        if(albumM==null){
            albumM = albumBo.guaranteeAlbumModel(metaSong,albumArtistM);
            artistBo.registerAlbumMtoArtistM(albumArtistM, albumM);
        }
        System.out.println(albumM);
        System.out.println(albumArtistM);

        //End with Meta, always add to relation
        System.out.println("META SCAN START");
        MetaModel metaM = metaBo.findByAlbumMandSongM(albumM,songM);
        if(metaM==null) {
            metaM = metaBo.guaranteeMetaModel(metaSong, albumM, songM);
            albumBo.registerMetaMtoAlbumM(albumM, metaM);
            songBo.registerMetaMtoSongM(songM, metaM);
        }
        //link with file
        fileBo.attachMetaMToFileM(fileM,metaM);
        metaBo.registerFileMtoMetaM(metaM,fileM);
        return metaM;

    }
}
