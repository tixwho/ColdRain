package local.generic;

import java.lang.reflect.Method;
import java.util.EnumMap;

public enum SupportedMeta {
    SRC("src"), FILENAME("fileName"), TRACKTITLE("trackTitle"), TRACKNO("trackNo"), DISCNO(
        "discNo"), TOTALDISCNO("totalDiscNo"), ARTIST("artist"), ALBUM(
            "album"), ALBUMARTIST("albumArtist"), FORMAT(
                "FORMAT"), SAMPLERATE("SAMPLERATE"), BITRATE("BITRATE"), LENGTH("LENGTH");

    private String property;

    SupportedMeta(String property) {
        this.property = property;
    }
    
    public String getMetaName() {
        return this.property;
    }


    public static Method getGetter(AbstractPlaylistSong songInstance, SupportedMeta meta)
        throws NoSuchMethodException, SecurityException {     
        String property_name = meta.property;
        String methodName =
            "get" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        // System.out.println("TEST in SongReader MethodName: " + methodName);
        Method gotGetMethod = songInstance.getClass().getMethod(methodName, String.class); // 逗号后相当于给一个固定类型的参
        return gotGetMethod;

    }
    
    public static Method getSetter(AbstractPlaylistSong songInstance, SupportedMeta meta)
        throws NoSuchMethodException, SecurityException {
        String property_name = meta.property;
        String methodName =
            "set" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        // System.out.println("TEST in SongReader MethodName: " + methodName);
        Method gotSetMethod = songInstance.getClass().getMethod(methodName, String.class); // 逗号后相当于给一个固定类型的参
        return gotSetMethod;

    }

    /**
     * Pack an EnumMap includes all SupportedMeta type, and all attributes have default value false.
     * 
     * @return An EnumMap in which all MetaType are set false.
     */
    public static EnumMap<SupportedMeta, Boolean> getInfoMap() {
        EnumMap<SupportedMeta, Boolean> fullFalseMap =
            new EnumMap<SupportedMeta, Boolean>(SupportedMeta.class);
        for (SupportedMeta eachInfotype : SupportedMeta.values()) {
            fullFalseMap.put(eachInfotype, false);
        }
        return fullFalseMap;
    }

    /**
     * Pack an EnumMap of all SupportedMeta type, in which attributes in array are set true.
     * 
     * @param supportedArr Array includes all supported SupportedMeta type.
     * @return An EnumMap in which corresponding MetaType are set true.
     */
    public static EnumMap<SupportedMeta, Boolean> getInfoMap(SupportedMeta[] supportedArr) {
        EnumMap<SupportedMeta, Boolean> rtrMap = SupportedMeta.getInfoMap();
        for (SupportedMeta eachType : supportedArr) {
            rtrMap.replace(eachType, true);
        }
        return rtrMap;

    }

    /**
     * Actually this method is useless cuz you can directly retrieve from map, but add a method here
     * might make the compare process looks neat.
     * 
     * @param checkMap The map to check for given table type.
     * @param meta SupportedMeta type to check.
     * @return If given meta is supported.
     */
    public static boolean isSupported(EnumMap<SupportedMeta, Boolean> checkMap,
        SupportedMeta meta) {
        return checkMap.get(meta);
    }
}
