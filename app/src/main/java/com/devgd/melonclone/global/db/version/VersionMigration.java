package com.devgd.melonclone.global.db.version;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

// DB 마이그레이션 용
public class VersionMigration {

    private static VersionMigration INSTANCE;

    public static VersionMigration getInstance() {
        if (INSTANCE == null)
            INSTANCE = new VersionMigration();

        return INSTANCE;
    }

    private VersionMigration() {
        this.migrationList.add(MIGRATION_1_2);
        this.migrationList.add(MIGRATION_2_3);
        this.migrationList.add(MIGRATION_3_4);
    }

    private List<Migration> migrationList = new ArrayList<>();

    public List<Migration> getMigrationList() {
        return this.migrationList;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `mind_table` (" +
                    "`mind_id` INTEGER, " +
                    "`mind_nm` TEXT, " +
                    "`select_number` INTEGER, " +
                    "PRIMARY KEY(`mind_id`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE cart_table");
            database.execSQL("CREATE TABLE `cart_table` (" +
                    "`mtrl_id` TEXT, " +
                    "`title` TEXT, " +
                    "`image_url` TEXT, " +
                    "`background_url` TEXT, " +
                    "`reg_time` TEXT, " +
                    "`reg_user_id` TEXT, " +
                    "`reg_user_nm` TEXT, " +
                    "`audio` TEXT, " +
                    "`category` TEXT, " +
                    "`category_code` TEXT, " +
                    "`themagroup` TEXT, " +
                    "`themagroup_id` INTEGER, " +
                    "`thema` TEXT, " +
                    "`thema_id` INTEGER, " +
                    "`like` INTEGER, " +
                    "`likeYn` BOOLEAN, " +
                    "`my` INTEGER, " +
                    "`myYn` BOOLEAN, " +
                    "`content` TEXT, " +
                    "`video` TEXT, " +
                    "`feed_url` TEXT, " +
                    "`mix_1` INTEGER, " +
                    "`mix_2` INTEGER, " +
                    "`mix_3` INTEGER, " +
                    "`mix_4` INTEGER, " +
                    "`mix_5` INTEGER, " +
                    "`playtime` INTEGER, " +
                    "PRIMARY KEY(`mtrl_id`))");

            database.execSQL("DROP TABLE playlist_table");
            database.execSQL("CREATE TABLE `playlist_table` (" +
                    "`mtrl_id` TEXT, " +
                    "`title` TEXT, " +
                    "`image_url` TEXT, " +
                    "`background_url` TEXT, " +
                    "`reg_time` TEXT, " +
                    "`reg_user_id` TEXT, " +
                    "`reg_user_nm` TEXT, " +
                    "`audio` TEXT, " +
                    "`category` TEXT, " +
                    "`category_code` TEXT, " +
                    "`themagroup` TEXT, " +
                    "`themagroup_id` INTEGER, " +
                    "`thema` TEXT, " +
                    "`thema_id` INTEGER, " +
                    "`like` INTEGER, " +
                    "`likeYn` BOOLEAN, " +
                    "`my` INTEGER, " +
                    "`myYn` BOOLEAN, " +
                    "`content` TEXT, " +
                    "`video` TEXT, " +
                    "`feed_url` TEXT, " +
                    "`mix_1` INTEGER, " +
                    "`mix_2` INTEGER, " +
                    "`mix_3` INTEGER, " +
                    "`mix_4` INTEGER, " +
                    "`mix_5` INTEGER, " +
                    "`playtime` INTEGER, " +
                    "PRIMARY KEY(`mtrl_id`))");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE cart_table");
            database.execSQL("CREATE TABLE `cart_table` (" +
                    "`id` INTEGER, " +
                    "`user_id` TEXT, " +
                    "`mtrl_id` TEXT, " +
                    "`title` TEXT, " +
                    "`image_url` TEXT, " +
                    "`background_url` TEXT, " +
                    "`reg_time` TEXT, " +
                    "`reg_user_id` TEXT, " +
                    "`reg_user_nm` TEXT, " +
                    "`audio` TEXT, " +
                    "`category` TEXT, " +
                    "`category_code` TEXT, " +
                    "`themagroup` TEXT, " +
                    "`themagroup_id` INTEGER, " +
                    "`thema` TEXT, " +
                    "`thema_id` INTEGER, " +
                    "`like` INTEGER, " +
                    "`likeYn` BOOLEAN, " +
                    "`my` INTEGER, " +
                    "`myYn` BOOLEAN, " +
                    "`content` TEXT, " +
                    "`video` TEXT, " +
                    "`feed_url` TEXT, " +
                    "`mix_1` INTEGER, " +
                    "`mix_2` INTEGER, " +
                    "`mix_3` INTEGER, " +
                    "`mix_4` INTEGER, " +
                    "`mix_5` INTEGER, " +
                    "`playtime` INTEGER, " +
                    "PRIMARY KEY(`id`))");

            database.execSQL("DROP TABLE playlist_table");
            database.execSQL("CREATE TABLE `playlist_table` (" +
                    "`id` INTEGER, " +
                    "`user_id` TEXT, " +
                    "`mtrl_id` TEXT, " +
                    "`title` TEXT, " +
                    "`image_url` TEXT, " +
                    "`background_url` TEXT, " +
                    "`reg_time` TEXT, " +
                    "`reg_user_id` TEXT, " +
                    "`reg_user_nm` TEXT, " +
                    "`audio` TEXT, " +
                    "`category` TEXT, " +
                    "`category_code` TEXT, " +
                    "`themagroup` TEXT, " +
                    "`themagroup_id` INTEGER, " +
                    "`thema` TEXT, " +
                    "`thema_id` INTEGER, " +
                    "`like` INTEGER, " +
                    "`likeYn` BOOLEAN, " +
                    "`my` INTEGER, " +
                    "`myYn` BOOLEAN, " +
                    "`content` TEXT, " +
                    "`video` TEXT, " +
                    "`feed_url` TEXT, " +
                    "`mix_1` INTEGER, " +
                    "`mix_2` INTEGER, " +
                    "`mix_3` INTEGER, " +
                    "`mix_4` INTEGER, " +
                    "`mix_5` INTEGER, " +
                    "`playtime` INTEGER, " +
                    "PRIMARY KEY(`id`))");
        }
    };
}
