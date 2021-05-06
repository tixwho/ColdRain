package com.coldrain.toolkit;

import com.coldrain.old.localModels.M3U8Song;
import com.coldrain.old.localModels.M3U8Table;
import com.coldrain.old.localModels.M3USong;
import com.coldrain.old.localModels.M3UTable;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NewFileWriter {


    public static void writeAM3U(M3UTable table, String outputAddr) throws IOException {

        ArrayList<M3USong> songArrlist = table.getSongArrrayList();
        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(outputAddr));
        // 把OutputStream再转成StreamWriter是为了从流式输入变为字符输入，同时指定utf8避免非英文字符乱码
        OutputStreamWriter oStreamWriter = new OutputStreamWriter(dataOut, StandardCharsets.UTF_8);
        for (M3USong song : songArrlist) {
            oStreamWriter.append(song.getSrc() + "\r\n");
        }
        oStreamWriter.close();
        dataOut.close();

    }

    public static void writeAnAppleM3U(M3UTable table, String outputAddr) throws IOException {

        ArrayList<M3USong> songArrlist = table.getSongArrrayList();
        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(outputAddr));
        // 把OutputStream再转成StreamWriter是为了从流式输入变为字符输入，同时指定utf8避免非英文字符乱码
        OutputStreamWriter oStreamWriter = new OutputStreamWriter(dataOut, StandardCharsets.UTF_8);
        for (M3USong song : songArrlist) {
            String toWrite = song.getSrc().replaceAll(".*?.Discography?", "../Discography");
            toWrite = toWrite.replaceAll("\\\\","/");
            toWrite = toWrite + "\r\n";
            oStreamWriter.append(toWrite);
        }
        oStreamWriter.close();
        dataOut.close();

    }

    public static void writeAM3U8(M3U8Table table, String outputAddr) throws IOException {

        ArrayList<M3U8Song> songArrlist = table.getSongArrrayList();
        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(outputAddr));
        // 把OutputStream再转成StreamWriter是为了从流式输入变为字符输入，同时指定utf8避免非英文字符乱码
        OutputStreamWriter oStreamWriter = new OutputStreamWriter(dataOut, StandardCharsets.UTF_8);
        String magicBytes = "#EXTINF:";
        for (M3U8Song song : songArrlist) {
            oStreamWriter.append(magicBytes);
            oStreamWriter.append(song.getUnknownNum() + ",");
            oStreamWriter.append(song.getTrackTitle() + "\r\n");
            oStreamWriter.append(song.getSrc() + "\r\n");
            oStreamWriter.append("\r\n");
        }
        oStreamWriter.close();
        dataOut.close();

    }

}
