package local.generic;

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
}
