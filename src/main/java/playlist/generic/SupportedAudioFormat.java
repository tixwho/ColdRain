package playlist.generic;

public enum SupportedAudioFormat {
    MP3("mp3", "Mp3"), FLAC("flac", "Flac");

    /**
     * File Suffix
     */
    private final String fileSuffix;

    /**
     * User Friendly Name
     */
    private final String displayName;

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
    SupportedAudioFormat(String fileSuffix, String displayName) {
        this.fileSuffix = fileSuffix;
        this.displayName = displayName;
    }
    
    public static String[] getSupportedAudioArray() {
        return new String[] {"mp3","flac"};
    }
}
