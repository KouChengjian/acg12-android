package com.liteutil.orm.db;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liteutil.annotation.Column;
import com.liteutil.annotation.Mapping;
import com.liteutil.annotation.PrimaryKey;
import com.liteutil.annotation.Table;
import com.liteutil.orm.db.assit.Checker;
import com.liteutil.orm.db.assit.Querier;
import com.liteutil.orm.db.assit.SQLBuilder;
import com.liteutil.orm.db.assit.SQLStatement;
import com.liteutil.orm.db.assit.Transaction;
import com.liteutil.orm.db.enums.AssignType;
import com.liteutil.orm.db.model.EntityTable;
import com.liteutil.orm.db.model.MapProperty;
import com.liteutil.orm.db.model.Primarykey;
import com.liteutil.orm.db.model.Property;
import com.liteutil.orm.db.model.SQLiteColumn;
import com.liteutil.orm.db.model.SQLiteTable;
import com.liteutil.util.DataUtil;
import com.liteutil.util.FieldUtil;
import com.liteutil.util.Log;

/**
 * 表管理
 *
 * @author MaTianyu
 * @date 2013-6-16上午12:27:32
 */
public final class TableManager {
    private static final String TAG = TableManager.class.getSimpleName();
    private static final String ID[] = new String[]{"id", "_id"};
    /**
     * 数据库表信息
     */
    private String dbName = "";
    /**
     * 这里放的是数据库表信息（表名、字段、建表语句...）
     * 每个数据库对应一个
     * key : Class Name
     * value: {@link EntityTable}
     */
    private HashMap<String, SQLiteTable> mSqlTableMap = new HashMap<String, SQLiteTable>();

    /**
     * 这里放的是类的实体信息表（主键、属性、关系映射...）
     * 全局单例
     * key : Class Name
     * value: {@link EntityTable}
     */
    private static HashMap<String, EntityTable> mEntityTableMap = new HashMap<String, EntityTable>();

    public TableManager(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 清空数据
     */
    public synchronized void clear() {
        mSqlTableMap = null;
        mEntityTableMap.clear();
    }

    /**
     * 检测表是否建立，没有则建一张新表。
     */
    public EntityTable checkOrCreateTable(SQLiteDatabase db, Object entity) {
        return checkOrCreateTable(db, entity.getClass());
    }

    /**
     * 检测[数据库表]是否建立，没有则建一张新表。
     */
    public synchronized EntityTable checkOrCreateTable(SQLiteDatabase db, Class claxx) {
        // 关键点0：[实体表]是否OK
        EntityTable table = getTable(claxx);
        //if (!table.isChecked) {
        // 关键点1：初始化全部数据库表
        initAllTablesFromSQLite(db);
        // table lock synchronized
        // 关键点2:判断[数据库表]是否存在，是否需要新加列。
        if (!checkExistAndColumns(db, table)) {
            // 关键点3：新建[数据库表]并加入表队列
            if (createTable(db, table)) {
                putNewSqlTableIntoMap(table);
            }
        }
        //    table.isChecked = true;
        //}
        return table;
    }

    /**
     * 检测[映射表]是否建立，没有则建一张新表。
     */
    public synchronized void checkOrCreateMappingTable(SQLiteDatabase db, String tableName,
                                                       String column1, String column2) {
        // 关键点0：[实体表]是否OK
        EntityTable table = getMappingTable(tableName, column1, column2);
        //if (!table.isChecked) {
        // 关键点1：初始化全部数据库表
        initAllTablesFromSQLite(db);
        // 关键点2:判断[数据库表]是否存在，是否需要新加列。
        if (!checkExistAndColumns(db, table)) {
            // 关键点3：新建[数据库表]并加入表队列
            if (createTable(db, table)) {
                putNewSqlTableIntoMap(table);
            }
        }
        //table.isChecked = true;
        //}
    }

    /**
     * 检查表是否存在，存在的话检查是否需要改动，添加列字段。
     * 注：sqlite仅仅支持表改名、表添加列两种alter方式。表中修改、刪除列是不被直接支持的。
     * 不能新加主键：The column may not have a PRIMARY KEY or UNIQUE constraint.
     * <p> http://www.sqlite.org/lang_altertable.html
     */
    private boolean checkExistAndColumns(SQLiteDatabase db, EntityTable entityTable) {
        if (!Checker.isEmpty(mSqlTableMap)) {
            SQLiteTable sqlTable = mSqlTableMap.get(entityTable.name);
            if (sqlTable != null) {
                if (Log.isPrint) {
                    Log.d(TAG, "Table [" + entityTable.name + "] Exist");
                }
                if (!sqlTable.isTableChecked) {
                    // 表仅进行一次检查，检验是否有新字段加入。
                    sqlTable.isTableChecked = true;
                    if (Log.isPrint) {
                    	Log.i(TAG, "Table [" + entityTable.name + "] check column now.");
                    }
                    if (entityTable.key != null) {
                        if (sqlTable.columns.get(entityTable.key.column) == null) {
                            SQLStatement stmt = SQLBuilder.buildDropTable(sqlTable.name);
                            stmt.execute(db);
                            if (Log.isPrint) {
                            	Log.i(TAG, "Table [" + entityTable.name + "] Primary Key has changed, " +
                                              "so drop and recreate it later.");
                            }
                            return false;
                        }
                    }
                    if (entityTable.pmap != null) {
                        ArrayList<String> newColumns = new ArrayList<String>();
                        for (String col : entityTable.pmap.keySet()) {
                            if (sqlTable.columns.get(col) == null) {
                                newColumns.add(col);
                            }
                        }
                        if (!Checker.isEmpty(newColumns)) {
                            for (String col : newColumns) {
                                sqlTable.columns.put(col, 1);
                            }
                            int sum = insertNewColunms(db, entityTable.name, newColumns);
                            if (Log.isPrint) {
                                if (sum > 0) {
                                    Log.i(TAG,
                                            "Table [" + entityTable.name + "] add " + sum + " new column ： " + newColumns);
                                } else {
                                	Log.e(TAG,
                                            "Table [" + entityTable.name + "] add " + sum + " new column error ： " +
                                            newColumns);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        if (Log.isPrint) {
        	Log.d(TAG, "Table [" + entityTable.name + "] Not Exist");
        }
        return false;
    }

    /**
     * 将Sql Table放入存储集合
     */
    private void putNewSqlTableIntoMap(EntityTable table) {
        if (Log.isPrint) {
        	Log.i(TAG, "Table [" + table.name + "] Create Success");
        }
        SQLiteTable sqlTable = new SQLiteTable();
        sqlTable.name = table.name;
        sqlTable.columns = new HashMap<String, Integer>();
        if (table.key != null) {
            sqlTable.columns.put(table.key.column, 1);
        }
        if (table.pmap != null) {
            for (String col : table.pmap.keySet()) {
                sqlTable.columns.put(col, 1);
            }
        }
        // 第一次建表，不用检查
        sqlTable.isTableChecked = true;
        mSqlTableMap.put(sqlTable.name, sqlTable);
    }

    /**
     * 初始化全部表及其列名,初始化失败，则无法进行下去。
     */
    private void initAllTablesFromSQLite(SQLiteDatabase db) {
        synchronized (mSqlTableMap) {
            if (Checker.isEmpty(mSqlTableMap)) {
                if (Log.isPrint) {
                	Log.i(TAG, "Initialize SQL table start--------------------->");
                }
                SQLStatement st = SQLBuilder.buildTableObtainAll();
                final EntityTable table = getTable(SQLiteTable.class, false);
                Querier.doQuery(db, st, new Querier.CursorParser() {
                    @Override
                    public void parseEachCursor(SQLiteDatabase db, Cursor c) throws Exception {
                        SQLiteTable sqlTable = new SQLiteTable();
                        DataUtil.injectDataToObject(c, sqlTable, table);
                        ArrayList<String> colS = getAllColumnsFromSQLite(db, sqlTable.name);
                        if (Checker.isEmpty(colS)) {
                            // 如果读数据库失败了，那么解析建表语句
                        	Log.e(TAG, "读数据库失败了，开始解析建表语句");
                            colS = transformSqlToColumns(sqlTable.sql);
                        }
                        sqlTable.columns = new HashMap<String, Integer>();
                        for (String col : colS) {
                            sqlTable.columns.put(col, 1);
                        }
                        if (Log.isPrint) {
                        	Log.i(TAG, "Find One SQL Table: " + sqlTable);
                        	Log.i(TAG, "Table Column: "+colS);
                        }
                        mSqlTableMap.put(sqlTable.name, sqlTable);
                    }
                });
                if (Log.isPrint) {
                	Log.i(TAG, "Initialize SQL table end  ---------------------> " + mSqlTableMap.size());
                }
            }
        }
    }

    /**
     * 插入新列
     */
    private int insertNewColunms(SQLiteDatabase db, final String tableName, final List<String> columns) {
        Integer size = null;
        if (!Checker.isEmpty(columns)) {
            size = Transaction.execute(db, new Transaction.Worker<Integer>() {
                @Override
                public Integer doTransaction(SQLiteDatabase db) {
                    for (String c : columns) {
                        SQLStatement stmt = SQLBuilder.buildAddColumnSql(tableName, c);
                        stmt.execute(db);
                    }
                    return columns.size();
                }
            });
        }
        return size == null ? 0 : size;
    }

    /**
     * 建立新表
     */
    private boolean createTable(SQLiteDatabase db, EntityTable table) {
        return SQLBuilder.buildCreateTable(table).execute(db);
    }

    /**
     * 数据库分析
     * 通过读数据库得到一张表的全部列名
     */
    public ArrayList<String> getAllColumnsFromSQLite(SQLiteDatabase db, final String tableName) {
        final EntityTable table = getTable(SQLiteColumn.class, false);
        final ArrayList<String> list = new ArrayList<String>();

        SQLStatement st = SQLBuilder.buildColumnsObtainAll(tableName);
        Querier.doQuery(db, st, new Querier.CursorParser() {
            @Override
            public void parseEachCursor(SQLiteDatabase db, Cursor c) throws Exception {
                SQLiteColumn col = new SQLiteColumn();
                DataUtil.injectDataToObject(c, col, table);
                list.add(col.name);
            }
        });

        return list;
    }

    /**
     * 语义分析
     * 依据表的sql“CREATE TABLE”建表语句得到一张表的全部列名。
     */
    public ArrayList<String> transformSqlToColumns(String sql) {
        if (sql != null) {
            int start = sql.indexOf("(");
            int end = sql.lastIndexOf(")");
            if (start > 0 && end > 0) {
                sql = sql.substring(start + 1, end);
                String cloumns[] = sql.split(",");
                ArrayList<String> colList = new ArrayList<String>();
                for (String col : cloumns) {
                    col = col.trim();
                    int endS = col.indexOf(" ");
                    if (endS > 0) {
                        col = col.substring(0, endS);
                    }
                    colList.add(col);
                }
                Log.e(TAG, "降级：语义分析表结构（" + colList.toString() + " , Origin SQL is: " + sql);
                return colList;
            }
        }
        return null;
    }

    /* —————————————————————————— 静态私有方法 ———————————————————————— */

    /**
     * 获取缓存实体表信息
     */
    private static EntityTable getEntityTable(String name) {
        return mEntityTableMap.get(name);
    }

    /**
     * 缓存的实体表信息
     *
     * @return 返回前一个和此Key相同的Value，没有则返回null。
     */
    private static EntityTable putEntityTable(String tableName, EntityTable entity) {
        return mEntityTableMap.put(tableName, entity);
    }

    /**
     * 获取映射表信息(Entity Table)
     * 注意映射表存储在MAP中，key 为 database name + table name， value 为 entity table。
     *
     * @return {@link EntityTable}
     */
    private EntityTable getMappingTable(String tableName, String column1, String column2) {
        EntityTable table = getEntityTable(dbName + tableName);
        if (table == null) {
            table = new EntityTable();
            table.name = tableName;
            table.pmap = new LinkedHashMap<String, Property>();
            table.pmap.put(column1, null);
            table.pmap.put(column2, null);
            TableManager.putEntityTable(dbName + tableName, table);
        }
        return table;
    }
    /* —————————————————————————— 静态公共方法 ———————————————————————— */

    /**
     * 根据实体生成表信息,一定需要PrimaryKey
     */
    public static EntityTable getTable(Object entity) {
        return getTable(entity.getClass(), true);
    }

    /**
     * 根据类生成表信息,一定需要PrimaryKey
     */
    public static EntityTable getTable(Class<?> claxx) {
        return getTable(claxx, true);
    }

    /**
     * 获取实体表信息(Entity Table)
     * 注意映射表存储在MAP中，key 为 class name， value 为 entity table。
     *
     * @return {@link EntityTable}
     */
    public static synchronized EntityTable getTable(Class<?> claxx, boolean needPK) {
        if (claxx == null) {
            return null;
        }
        EntityTable table = getEntityTable(claxx.getName());
        //if(OrmLog.isPrint)OrmLog.i(TAG, "table : " + table + "  , claxx: " + claxx);
        if (table == null) {
            table = new EntityTable();
            table.claxx = claxx;
            table.name = getTableName(claxx);
            table.pmap = new LinkedHashMap<String, Property>();
            List<Field> fields = FieldUtil.getAllDeclaredFields(claxx);
            for (Field f : fields) {
                if (FieldUtil.isInvalid(f)) {
                    continue;
                }
                Property p = new Property();
                p.field = f;
                // 获取列名,每个属性都有，没有注解默认取属性名
                //if(OrmLog.isPrint)OrmLog.i(TAG, "Column : " + Column.class+ "  field: "+ f);
                Column col = f.getAnnotation(Column.class);
                if (col != null) {
                    p.column = col.value();
                } else {
                    p.column = f.getName();
                }

                // 主键判断
                //if(OrmLog.isPrint)OrmLog.i(TAG, "Primarykey : " + Primarykey.class + "  field: "+ f + " asst:" + AssignType.AUTO_INCREMENT);
                PrimaryKey key = f.getAnnotation(PrimaryKey.class);
                if (key != null) {
                    // 主键不加入属性Map
                    table.key = new Primarykey(p, key.value());
                    // 主键为系统分配,对类型有要求
                    checkPrimaryKey(table.key);
                } else {
                    //ORM handle
                    //if(OrmLog.isPrint)OrmLog.i(TAG, "Mapping : " + Mapping.class+ " field: "+ f);
                    Mapping mapping = f.getAnnotation(Mapping.class);
                    if (mapping != null) {
                        table.addMapping(new MapProperty(p, mapping.value()));
                    } else {
                        table.pmap.put(p.column, p);
                    }
                }
            }
            if (table.key == null) {
                for (String col : table.pmap.keySet()) {
                    for (String id : ID) {
                        if (id.equalsIgnoreCase(col)) {
                            Property p = table.pmap.get(col);
                            if (p.field.getType() == String.class) {
                                // 主键移除属性Map
                                table.pmap.remove(col);
                                table.key = new Primarykey(p, AssignType.BY_MYSELF);
                                break;
                            } else if (FieldUtil.isNumber(p.field.getType())) {
                                // 主键移除属性Map
                                table.pmap.remove(col);
                                table.key = new Primarykey(p, AssignType.AUTO_INCREMENT);
                                break;
                            }

                        }
                    }
                    if (table.key != null) {
                        break;
                    }
                }
            }
            if (needPK && table.key == null) {
                throw new RuntimeException(
                        "你必须为[" + table.claxx.getSimpleName() + "]设置主键(you must set the primary key...)" +
                        "\n 提示：在对象的属性上加PrimaryKey注解来设置主键。");
            }
            putEntityTable(claxx.getName(), table);
        }
        return table;
    }

    private static void checkPrimaryKey(Primarykey key) {
        if (key.isAssignedBySystem()) {
            if (!FieldUtil.isNumber(key.field.getType())) {
                throw new RuntimeException(
                        AssignType.AUTO_INCREMENT
                        + " Auto increment primary key must be a number ...\n " +
                        "错误提示：自增主键必须设置为数字类型");
            }
        } else if (key.isAssignedByMyself()) {
            if (String.class != key.field.getType() && !FieldUtil.isNumber(key.field.getType())) {
                throw new RuntimeException(
                        AssignType.BY_MYSELF
                        + " Custom primary key must be string or number ...\n " +
                        "错误提示：自定义主键值必须为String或者Number类型");
            }
        } else {
            throw new RuntimeException(
                    " Primary key without Assign Type ...\n " +
                    "错误提示：主键无类型");
        }
    }

    /**
     * 根据类自动生成表名字
     */
    public static String getTableName(Class<?> claxx) {
        Table anno = claxx.getAnnotation(Table.class);
        if (anno != null) {
            return anno.value();
        } else {
            return claxx.getName().replaceAll("\\.", "_");
        }
    }

    public static String getMapTableName(Class c1, Class c2) {
        return getMapTableName(getTableName(c1), getTableName(c2));
    }

    public static String getMapTableName(EntityTable t1, EntityTable t2) {
        return getMapTableName(t1.name, t2.name);
    }

    public static String getMapTableName(String tableName1, String tableName2) {
        if (tableName1.compareTo(tableName2) < 0) {
            return tableName1 + "_" + tableName2;
        } else {
            return tableName2 + "_" + tableName1;
        }
    }

}
