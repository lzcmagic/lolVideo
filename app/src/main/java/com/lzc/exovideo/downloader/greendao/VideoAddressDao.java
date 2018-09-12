package com.lzc.exovideo.downloader.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lzc.exovideo.db.entity.VideoAddress;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO_ADDRESS".
*/
public class VideoAddressDao extends AbstractDao<VideoAddress, Long> {

    public static final String TABLENAME = "VIDEO_ADDRESS";

    /**
     * Properties of entity VideoAddress.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property BelongTo = new Property(1, String.class, "belongTo", false, "BELONG_TO");
        public final static Property Url = new Property(2, String.class, "url", false, "URL");
    }


    public VideoAddressDao(DaoConfig config) {
        super(config);
    }
    
    public VideoAddressDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO_ADDRESS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"BELONG_TO\" TEXT," + // 1: belongTo
                "\"URL\" TEXT);"); // 2: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO_ADDRESS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, VideoAddress entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String belongTo = entity.getBelongTo();
        if (belongTo != null) {
            stmt.bindString(2, belongTo);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, VideoAddress entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String belongTo = entity.getBelongTo();
        if (belongTo != null) {
            stmt.bindString(2, belongTo);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public VideoAddress readEntity(Cursor cursor, int offset) {
        VideoAddress entity = new VideoAddress( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // belongTo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // url
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, VideoAddress entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setBelongTo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(VideoAddress entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(VideoAddress entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(VideoAddress entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
