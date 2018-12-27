package com.acg12.utlis;

import android.database.sqlite.SQLiteDatabase;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.SQLiteHelper;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import com.acg12.app.MyApplication;
import com.acg12.constant.Constant;

import java.util.Collection;
import java.util.List;

public enum DBImpl implements SQLiteHelper.OnUpdateListener {

    INSTANCE;
    private LiteOrm mDataBase;

    DBImpl() {
        DataBaseConfig config = new DataBaseConfig(MyApplication.getInstance());
        config.dbName = Constant.DB_NAME;
        config.dbVersion = 1;
        config.onUpdateListener = this;
        mDataBase = LiteOrm.newCascadeInstance(config);
    }

    @Override
    public void onUpdate(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    /**
     * save: insert or update a single entity
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    public static <T> long save(T t) {
        if (t == null) {
            return -1;
        }
        return DBImpl.INSTANCE.mDataBase.save(t);
    }

    /**
     * save: insert or update a collection
     *
     * @return the number of affected rows
     */
    public static <T> int save(Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return DBImpl.INSTANCE.mDataBase.save(list);
    }

    /**
     * insert a collection
     *
     * @return the number of affected rows
     */
    public static <T> long insert(Collection<T> tt) {
        if (tt == null) {
            return -1;
        }
        return DBImpl.INSTANCE.mDataBase.insert(tt);
    }

    /**
     * insert a single entity
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    public static <T> long insert(T t, ConflictAlgorithm var2) {
        if (t == null) {
            return -1;
        }
        return DBImpl.INSTANCE.mDataBase.insert(t, var2);
    }

    /**
     * insert a single entity with conflict algorithm
     *
     * @return the number of rows affected by this SQL statement execution.
     */
    public static <T> int save(Collection<T> list, ConflictAlgorithm var2) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return DBImpl.INSTANCE.mDataBase.insert(list, var2);
    }

    /**
     * query all data of this type
     *
     * @return the query result list
     */
    public static <T> Collection<T> query(Class<T> cla) {
        return DBImpl.INSTANCE.mDataBase.query(cla);
    }

    public static <T> List<T> query(Class<T> cla, String field, Object value) {
        QueryBuilder<T> queryBuilder = new QueryBuilder<T>(cla).whereEquals(field, value);
        return DBImpl.INSTANCE.mDataBase.<T>query(queryBuilder);
    }

    public static <T> List<T> query(Class<T> cla, String field, Object[] value) {
        return DBImpl.INSTANCE.mDataBase.<T>query(new QueryBuilder<T>(cla).where(field + "=?", value));
    }

    public static <T> List<T> query(Class<T> cla, String field, Object[] value, int start, int length) {
        QueryBuilder<T> queryBuilder = new QueryBuilder<T>(cla).where(field + "=?", value).limit(start, length);
        return DBImpl.INSTANCE.mDataBase.<T>query(queryBuilder);
    }

    /**
     * custom query
     *
     * @return the query result list
     */
    public static <T> List<T> query(QueryBuilder<T> var1) {
        return DBImpl.INSTANCE.mDataBase.<T>query(var1);
    }

    /**
     * query entity by long id
     *
     * @return the query result
     */
    public static <T> T queryById(Class<T> clazz, long id) {
        return DBImpl.INSTANCE.mDataBase.<T>queryById(id, clazz);
    }

    /**
     * query entity by string id
     *
     * @return the query result
     */
    public static <T> T queryById(Class<T> clazz, String id) {
        return DBImpl.INSTANCE.mDataBase.<T>queryById(id, clazz);
    }

    /**
     * query count of table rows and return
     *
     * @return the count of query result
     */
    public static <T> long queryCount(Class<T> claxx) {
        return DBImpl.INSTANCE.mDataBase.<T>queryCount(claxx);
    }

    /**
     * query count of your sql query result rows and return
     *
     * @return the count of query result
     */
    public static <T> long queryCount(QueryBuilder qb) {
        return DBImpl.INSTANCE.mDataBase.<T>queryCount(qb);
    }

    /**
     * query count of your sql query result rows and return
     *
     * @return the count of query result
     */
    public static <T> long queryCount(Class<T> cla, String field, Object value) {
        QueryBuilder<T> queryBuilder = new QueryBuilder<T>(cla).whereEquals(field, value);
        return DBImpl.INSTANCE.mDataBase.<T>queryCount(queryBuilder);
    }

    /**
     * update a single entity
     * ConflictAlgorithm:ConflictAlgorithm.Replace
     *
     * @return number of affected rows
     */
    public static <T> int update(T t) {
        return DBImpl.INSTANCE.mDataBase.update(t, ConflictAlgorithm.Replace);
    }

    /**
     * update a single entity with conflict algorithm
     *
     * @return number of affected rows
     */
    public static <T> int update(T t, ConflictAlgorithm conflictAlgorithm) {
        return DBImpl.INSTANCE.mDataBase.update(t, conflictAlgorithm);
    }

    /**
     * update a single entity with conflict algorithm, and only update columns in {@link ColumnsValue}
     * if param {@link ColumnsValue} is null, update all columns.
     *
     * @return the number of affected rows
     */
    public static <T> int update(T t, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
        return DBImpl.INSTANCE.mDataBase.update(t, cvs, conflictAlgorithm);
    }

    /**
     * update a single entity
     *
     * @return number of affected rows
     */
    public static <T> int update0(T t) {
        return DBImpl.INSTANCE.mDataBase.update(t);
    }

    /**
     * update a collection
     *
     * @return the number of affected rows
     */
    public static <T> int update(Collection<T> collection) {
        return DBImpl.INSTANCE.mDataBase.update(collection, ConflictAlgorithm.Replace);
    }

    /**
     * update a collection with conflict algorithm, and only update columns in {@link ColumnsValue}
     * if param {@link ColumnsValue} is null, update all columns.
     *
     * @return number of affected rows
     */
    public static <T> int update(Collection<T> collection, ConflictAlgorithm conflictAlgorithm) {
        return DBImpl.INSTANCE.mDataBase.update(collection, conflictAlgorithm);
    }

    /**
     * update a single entity with conflict algorithm, and only update columns in {@link ColumnsValue}
     * if param {@link ColumnsValue} is null, update all columns.
     *
     * @return the number of affected rows
     */
    public static <T> int update(Collection<T> collection, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
        return DBImpl.INSTANCE.mDataBase.update(collection, cvs, conflictAlgorithm);
    }

    public static <T> int update0(Collection<T> collection) {
        return DBImpl.INSTANCE.mDataBase.update(collection);
    }

    /**
     * update model use custom where clause.
     *
     * @return number of affected rows
     */
    public static int update(WhereBuilder builder, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
        return DBImpl.INSTANCE.mDataBase.update(builder, cvs, conflictAlgorithm);
    }

    /**
     * delete a single entity
     *
     * @return the number of affected rows
     */
    public static <T> int delete(T t) {
        return DBImpl.INSTANCE.mDataBase.delete(t);
    }

    public static <T> int delete(Class<T> cla, String field, Object value) {
        return DBImpl.INSTANCE.mDataBase.delete(WhereBuilder.create(cla).where(field + "=?", value));
    }

    /**
     * delete all rows
     *
     * @return the number of affected rows
     */
    public static <T> int delete(Class<T> cla) {
        return DBImpl.INSTANCE.mDataBase.delete(cla);
    }

    /**
     * delete all rows
     *
     * @return the number of affected rows
     */
    public static <T> int deleteAll(Class<T> claxx) {
        return DBImpl.INSTANCE.mDataBase.deleteAll(claxx);
    }

    /**
     * <b>start must >=0 and smaller than end</b>
     * <p>delete from start to the end, <b>[start,end].</b>
     * <p>set end={@link Integer#MAX_VALUE} will delete all rows from the start
     *
     * @return the number of affected rows
     */
    public static <T> int delete(Class<T> claxx, long start, long end, String orderAscColu) {
        return DBImpl.INSTANCE.mDataBase.delete(claxx, start, end, orderAscColu);
    }

    /**
     * delete a collection
     *
     * @return the number of affected rows
     */
    public static <T> int delete(Collection<T> collection) {
        return DBImpl.INSTANCE.mDataBase.delete(collection);
    }

    /**
     * delete by custem where syntax
     *
     * @return the number of affected rows
     * @deprecated use {@link #delete(WhereBuilder)} instead.
     */
    public static <T> int delete(Class<T> claxx, WhereBuilder where) {
        return DBImpl.INSTANCE.mDataBase.delete(claxx, where);
    }

    /**
     * delete by custem where syntax
     *
     * @return the number of affected rows
     */
    public static int delete(WhereBuilder where) {
        return DBImpl.INSTANCE.mDataBase.delete(where);
    }

}
