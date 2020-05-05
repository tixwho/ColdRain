package apps.oldLocalApps;

/*Algorithm
 * Prework:Split different disc into different folders, with same meta for album.
 * NamePattern: AlbumAAA Disc1;AlbumAAA Disc2
 * Arg1:FolderName Preset (most of time, album name) e.g. AlbumAAA
 * Arg2:parent folder (folder that contains "AlbumAAA Disc1")
 * 
 * First: search for all folders match name pattern "AlbumAAA Disc", pack into a map, count disc.
 * *Map Entry<discFolderPath,disc>
 * Second: create new folders if not exist.
 * Third: iterate through map, for each discpath, write in disc and totalDisc Meta, move to new 
 * folder(albumName/Disc N),ready to update related playlist info.
 * Fourth: update playlist.
 */
public class DiscMerger {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
