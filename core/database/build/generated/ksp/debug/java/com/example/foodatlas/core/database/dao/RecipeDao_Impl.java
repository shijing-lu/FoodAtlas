package com.example.foodatlas.core.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.foodatlas.core.database.model.IngredientEntity;
import com.example.foodatlas.core.database.model.RecipeEntity;
import com.example.foodatlas.core.database.model.RecipeWithDetails;
import com.example.foodatlas.core.database.model.StepEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecipeEntity> __insertionAdapterOfRecipeEntity;

  private final EntityInsertionAdapter<IngredientEntity> __insertionAdapterOfIngredientEntity;

  private final EntityInsertionAdapter<StepEntity> __insertionAdapterOfStepEntity;

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __deletionAdapterOfRecipeEntity;

  public RecipeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipeEntity = new EntityInsertionAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recipes` (`id`,`title`,`imageUrl`,`lastCookedInfo`,`category`,`cookTime`,`difficulty`,`cookCount`,`daysSinceLastCook`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getImageUrl());
        statement.bindString(4, entity.getLastCookedInfo());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getCookTime());
        statement.bindString(7, entity.getDifficulty());
        statement.bindLong(8, entity.getCookCount());
        if (entity.getDaysSinceLastCook() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDaysSinceLastCook());
        }
      }
    };
    this.__insertionAdapterOfIngredientEntity = new EntityInsertionAdapter<IngredientEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `ingredients` (`id`,`recipeId`,`name`,`amount`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IngredientEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRecipeId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getAmount());
      }
    };
    this.__insertionAdapterOfStepEntity = new EntityInsertionAdapter<StepEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `steps` (`id`,`recipeId`,`stepDescription`,`stepOrder`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StepEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRecipeId());
        statement.bindString(3, entity.getStepDescription());
        statement.bindLong(4, entity.getStepOrder());
      }
    };
    this.__deletionAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recipes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insertRecipe(final RecipeEntity recipe,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecipeEntity.insertAndReturnId(recipe);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertIngredients(final List<IngredientEntity> ingredients,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfIngredientEntity.insert(ingredients);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSteps(final List<StepEntity> steps,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStepEntity.insert(steps);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecipe(final RecipeEntity recipe,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecipeEntity.handle(recipe);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRecipeWithDetails(final RecipeEntity recipe,
      final List<IngredientEntity> ingredients, final List<StepEntity> steps,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> RecipeDao.DefaultImpls.insertRecipeWithDetails(RecipeDao_Impl.this, recipe, ingredients, steps, __cont), $completion);
  }

  @Override
  public Flow<List<RecipeWithDetails>> getAllRecipes() {
    final String _sql = "SELECT * FROM recipes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"ingredients", "steps",
        "recipes"}, new Callable<List<RecipeWithDetails>>() {
      @Override
      @NonNull
      public List<RecipeWithDetails> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfLastCookedInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCookedInfo");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final int _cursorIndexOfCookTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookTime");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfCookCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cookCount");
            final int _cursorIndexOfDaysSinceLastCook = CursorUtil.getColumnIndexOrThrow(_cursor, "daysSinceLastCook");
            final LongSparseArray<ArrayList<IngredientEntity>> _collectionIngredients = new LongSparseArray<ArrayList<IngredientEntity>>();
            final LongSparseArray<ArrayList<StepEntity>> _collectionSteps = new LongSparseArray<ArrayList<StepEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionIngredients.containsKey(_tmpKey)) {
                _collectionIngredients.put(_tmpKey, new ArrayList<IngredientEntity>());
              }
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionSteps.containsKey(_tmpKey_1)) {
                _collectionSteps.put(_tmpKey_1, new ArrayList<StepEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipingredientsAscomExampleFoodatlasCoreDatabaseModelIngredientEntity(_collectionIngredients);
            __fetchRelationshipstepsAscomExampleFoodatlasCoreDatabaseModelStepEntity(_collectionSteps);
            final List<RecipeWithDetails> _result = new ArrayList<RecipeWithDetails>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final RecipeWithDetails _item;
              final RecipeEntity _tmpRecipe;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              final String _tmpImageUrl;
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              final String _tmpLastCookedInfo;
              _tmpLastCookedInfo = _cursor.getString(_cursorIndexOfLastCookedInfo);
              final String _tmpCategory;
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
              final String _tmpCookTime;
              _tmpCookTime = _cursor.getString(_cursorIndexOfCookTime);
              final String _tmpDifficulty;
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
              final int _tmpCookCount;
              _tmpCookCount = _cursor.getInt(_cursorIndexOfCookCount);
              final Integer _tmpDaysSinceLastCook;
              if (_cursor.isNull(_cursorIndexOfDaysSinceLastCook)) {
                _tmpDaysSinceLastCook = null;
              } else {
                _tmpDaysSinceLastCook = _cursor.getInt(_cursorIndexOfDaysSinceLastCook);
              }
              _tmpRecipe = new RecipeEntity(_tmpId,_tmpTitle,_tmpImageUrl,_tmpLastCookedInfo,_tmpCategory,_tmpCookTime,_tmpDifficulty,_tmpCookCount,_tmpDaysSinceLastCook);
              final ArrayList<IngredientEntity> _tmpIngredientsCollection;
              final long _tmpKey_2;
              _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
              _tmpIngredientsCollection = _collectionIngredients.get(_tmpKey_2);
              final ArrayList<StepEntity> _tmpStepsCollection;
              final long _tmpKey_3;
              _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              _tmpStepsCollection = _collectionSteps.get(_tmpKey_3);
              _item = new RecipeWithDetails(_tmpRecipe,_tmpIngredientsCollection,_tmpStepsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<RecipeWithDetails> getRecipeById(final long id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"ingredients", "steps",
        "recipes"}, new Callable<RecipeWithDetails>() {
      @Override
      @Nullable
      public RecipeWithDetails call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfLastCookedInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCookedInfo");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final int _cursorIndexOfCookTime = CursorUtil.getColumnIndexOrThrow(_cursor, "cookTime");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfCookCount = CursorUtil.getColumnIndexOrThrow(_cursor, "cookCount");
            final int _cursorIndexOfDaysSinceLastCook = CursorUtil.getColumnIndexOrThrow(_cursor, "daysSinceLastCook");
            final LongSparseArray<ArrayList<IngredientEntity>> _collectionIngredients = new LongSparseArray<ArrayList<IngredientEntity>>();
            final LongSparseArray<ArrayList<StepEntity>> _collectionSteps = new LongSparseArray<ArrayList<StepEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionIngredients.containsKey(_tmpKey)) {
                _collectionIngredients.put(_tmpKey, new ArrayList<IngredientEntity>());
              }
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionSteps.containsKey(_tmpKey_1)) {
                _collectionSteps.put(_tmpKey_1, new ArrayList<StepEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipingredientsAscomExampleFoodatlasCoreDatabaseModelIngredientEntity(_collectionIngredients);
            __fetchRelationshipstepsAscomExampleFoodatlasCoreDatabaseModelStepEntity(_collectionSteps);
            final RecipeWithDetails _result;
            if (_cursor.moveToFirst()) {
              final RecipeEntity _tmpRecipe;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpTitle;
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              final String _tmpImageUrl;
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              final String _tmpLastCookedInfo;
              _tmpLastCookedInfo = _cursor.getString(_cursorIndexOfLastCookedInfo);
              final String _tmpCategory;
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
              final String _tmpCookTime;
              _tmpCookTime = _cursor.getString(_cursorIndexOfCookTime);
              final String _tmpDifficulty;
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
              final int _tmpCookCount;
              _tmpCookCount = _cursor.getInt(_cursorIndexOfCookCount);
              final Integer _tmpDaysSinceLastCook;
              if (_cursor.isNull(_cursorIndexOfDaysSinceLastCook)) {
                _tmpDaysSinceLastCook = null;
              } else {
                _tmpDaysSinceLastCook = _cursor.getInt(_cursorIndexOfDaysSinceLastCook);
              }
              _tmpRecipe = new RecipeEntity(_tmpId,_tmpTitle,_tmpImageUrl,_tmpLastCookedInfo,_tmpCategory,_tmpCookTime,_tmpDifficulty,_tmpCookCount,_tmpDaysSinceLastCook);
              final ArrayList<IngredientEntity> _tmpIngredientsCollection;
              final long _tmpKey_2;
              _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
              _tmpIngredientsCollection = _collectionIngredients.get(_tmpKey_2);
              final ArrayList<StepEntity> _tmpStepsCollection;
              final long _tmpKey_3;
              _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              _tmpStepsCollection = _collectionSteps.get(_tmpKey_3);
              _result = new RecipeWithDetails(_tmpRecipe,_tmpIngredientsCollection,_tmpStepsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipingredientsAscomExampleFoodatlasCoreDatabaseModelIngredientEntity(
      @NonNull final LongSparseArray<ArrayList<IngredientEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipingredientsAscomExampleFoodatlasCoreDatabaseModelIngredientEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`recipeId`,`name`,`amount` FROM `ingredients` WHERE `recipeId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "recipeId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfRecipeId = 1;
      final int _cursorIndexOfName = 2;
      final int _cursorIndexOfAmount = 3;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<IngredientEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final IngredientEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final long _tmpRecipeId;
          _tmpRecipeId = _cursor.getLong(_cursorIndexOfRecipeId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          final String _tmpAmount;
          _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
          _item_1 = new IngredientEntity(_tmpId,_tmpRecipeId,_tmpName,_tmpAmount);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipstepsAscomExampleFoodatlasCoreDatabaseModelStepEntity(
      @NonNull final LongSparseArray<ArrayList<StepEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipstepsAscomExampleFoodatlasCoreDatabaseModelStepEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`recipeId`,`stepDescription`,`stepOrder` FROM `steps` WHERE `recipeId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "recipeId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfRecipeId = 1;
      final int _cursorIndexOfStepDescription = 2;
      final int _cursorIndexOfStepOrder = 3;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<StepEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final StepEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final long _tmpRecipeId;
          _tmpRecipeId = _cursor.getLong(_cursorIndexOfRecipeId);
          final String _tmpStepDescription;
          _tmpStepDescription = _cursor.getString(_cursorIndexOfStepDescription);
          final int _tmpStepOrder;
          _tmpStepOrder = _cursor.getInt(_cursorIndexOfStepOrder);
          _item_1 = new StepEntity(_tmpId,_tmpRecipeId,_tmpStepDescription,_tmpStepOrder);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
