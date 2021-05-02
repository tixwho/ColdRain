package playlist.generic;

import java.lang.reflect.Method;
import java.util.EnumMap;

public enum SupportedMeta {
    SRC("src"), FILENAME("fileName"), TRACKTITLE("trackTitle"), TRACKNO("trackNo"), DISCNO(
        "discNo"), TOTALDISCNO("totalDiscNo"), ARTIST("artist"), ALBUM(
            "album"), ALBUMARTIST("albumArtist"), FORMAT(
                "FORMAT"), SAMPLERATE("SAMPLERATE"), BITRATE("BITRATE"), LENGTH("LENGTH");

    private final String property;

    SupportedMeta(String property) {
        this.property = property;
    }

    public String getProperty() {
        return this.property;
    }


    public static Method getGetter(AbstractPlaylistSong songInstance, SupportedMeta meta)
        throws NoSuchMethodException, SecurityException {
        String property_name = meta.property;
        String methodName =
            "get" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        //System.out.println("TEST in SupportedMeta MethodName: " + methodName);
        Method gotGetMethod = songInstance.getClass().getMethod(methodName); // 千万别给getter入参，谁给谁傻逼
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

    // explicitly cross out MetaSong so no need to extend MetaSong and prevent confusion.

    public static Method getGetter(MetaSong songInstance, SupportedMeta meta)
        throws NoSuchMethodException, SecurityException {
        String property_name = meta.property;
        String methodName =
            "get" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        // System.out.println("TEST in SupportedMeta MethodName: " + methodName);
        Method gotGetMethod = songInstance.getClass().getMethod(methodName); // 逗号后相当于给一个固定类型的参
        return gotGetMethod;

    }

    public static Method getSetter(MetaSong songInstance, SupportedMeta meta)
        throws NoSuchMethodException, SecurityException {
        String property_name = meta.property;
        String methodName =
            "set" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        // System.out.println("TEST in SupportedMeta MethodName: " + methodName);
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
     * Return EnumMap setting all attributes true/false as input
     * 
     * @param bool Default value for map
     * @return EnumMap covers all EnumType with default value.
     */
    public static EnumMap<SupportedMeta, Boolean> getInfoMap(boolean bool) {
        if (bool == true) {
            EnumMap<SupportedMeta, Boolean> fullTrueMap =
                new EnumMap<SupportedMeta, Boolean>(SupportedMeta.class);
            for (SupportedMeta eachInfotype : SupportedMeta.values()) {
                fullTrueMap.put(eachInfotype, true);
            }
            return fullTrueMap;
        } else {
            return getInfoMap();
        }
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
