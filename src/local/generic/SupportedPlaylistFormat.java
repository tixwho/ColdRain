package local.generic;

import local.m3u.M3uTable;
import local.m3u8.M3u8Table;

public enum SupportedPlaylistFormat {
    M3U("m3u", "M3u"), M3U8("m3u8", "M3u8"), ZPL("zpl", "Zpl");

    /**
     * File Suffix
     */
    private String fileSuffix;

    /**
     * User Friendly Name
     */
    private String displayName;

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Constructor for internal use in this enum
     * 
     * @param fileSuffix
     * @param displayName
     */
    SupportedPlaylistFormat(String fileSuffix, String displayName) {
        this.fileSuffix = fileSuffix;
        this.displayName = displayName;
    }

    // regularly updated with supported playlist type.
    public static Class<?> getPlaylistClass(SupportedPlaylistFormat format) {
        String suffix = format.getFileSuffix();
        Class<?> rtrclazz = null;
        switch (suffix) {
            case "m3u":
                rtrclazz = M3uTable.class;
                break;
            case "m3u8":
                rtrclazz = M3u8Table.class;
                break;

        }
        
        return rtrclazz;
    }
}
