package com.devgd.melonclone.global.initiator;

import androidx.room.RoomDatabase;

import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.db.version.AppDatabase;
import com.devgd.melonclone.global.db.version.VersionMigration;

import org.watermelon.framework.global.db.version.MigrationContainer;
import org.watermelon.framework.global.model.application.DatabaseInitiator;

public class MelonDatabaseInitiator extends DatabaseInitiator {

    @Override
    public String getDatabaseName() {
        return Constants.DB_NAME;
    }

    @Override
    public MigrationContainer getMigrationContainer() {
        return new VersionMigration();
    }

    @Override
    public <D extends RoomDatabase> Class<D> getDatabaseClass() {
        return (Class<D>) AppDatabase.class;
    }
}
