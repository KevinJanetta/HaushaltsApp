package com.example.lebensmittelverwaltung.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lebensmittelverwaltung.data.db.entities.Einkaeufe
import com.example.lebensmittelverwaltung.data.db.entities.Produkt
import com.example.lebensmittelverwaltung.data.db.entities.ProduktVorrat


@Database(
    entities = [Produkt::class, ProduktVorrat::class, Einkaeufe::class], // Welche Entitäten die Datenbank enthält
    version = 7   // Die Version der Datenbank, in dem Fall Version 7, weil sie 7 mal geändert wurde
)

abstract class EinkaufDatenbank : RoomDatabase() {

    abstract fun getEinkaufsDao() : EinkaufsDao
    abstract fun getVorratsDao() : VorratsDao
    abstract fun getEinkaeufeDao() : EinkaeufeDao

    companion object {
        @Volatile
        private var instance : EinkaufDatenbank? = null
        private val LOCK = Any()


        //Wenn eine Instanz gebildet wird wird die Funktion abgerufen
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: erstelleDatenbank(context).also { instance = it} //es wird geprüft, ob bereits eine Instanz der Datenbank besteht
                                                                         //wenn dies nicht der fall ist, wird eine Instanz erstellt
        }

        private fun erstelleDatenbank(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                EinkaufDatenbank::class.java, "EinkaufsDB.db").addMigrations(MIGRATION_1_2(), MIGRATION_2_3(), //erstellen der Datenbank, mit ihren vorhandenen Veränderungen
                MIGRATION_3_4(),MIGRATION_4_5(), MIGRATION_5_6(), MIGRATION_6_7()).build()
    }

    class MIGRATION_1_2: Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `vorrat_produkt` (`id` INTEGER,`vprodukt_name` TEXT NOT NULL, `vprodukt_anzahl` INTEGER NOT NULL, `vprodukt_datum` TEXT, PRIMARY KEY(`id`))")
        }
    }

    class MIGRATION_2_3: Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `einkaeufe` (`id` INTEGER,`einkauf_name` TEXT NOT NULL, `gesamtpreis` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        }
    }

    class MIGRATION_3_4: Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `einkaufs_produkt`" + " ADD COLUMN `produkt_preis` DOUBLE");
        }
    }

    class MIGRATION_4_5: Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `einkaufs_produkt`" + " ADD COLUMN `produkt_gekauft` INTEGER");
        }
    }

    class MIGRATION_5_6: Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE `einkaeufe`");
            database.execSQL("CREATE TABLE IF NOT EXISTS `einkaeufe` (`id` INTEGER,`einkauf_name` TEXT NOT NULL, `gesamtpreis` DOUBLE NOT NULL, PRIMARY KEY(`id`))")
        }
    }

    class MIGRATION_6_7: Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `vorrat_produkt`" + " ADD COLUMN `vprodukt_expiringSoon` INTEGER");
        }
    }

}