package com.coldrain.database.dao;

import com.coldrain.database.models.PlaylistModel;
import com.coldrain.database.models.PlaylistRecordModel;
import java.util.List;

public interface PlaylistRecordDao {

    void save(PlaylistRecordModel playlistRecordM);
    void update(PlaylistRecordModel playlistRecordM);
    void delete(PlaylistRecordModel playlistRecordM);

    //query
    List<PlaylistRecordModel> findByPlaylistM(PlaylistModel playlistM);

}
