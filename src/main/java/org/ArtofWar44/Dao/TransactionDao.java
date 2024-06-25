package org.ArtofWar44.Dao;

public interface TransactionDao {
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
}
