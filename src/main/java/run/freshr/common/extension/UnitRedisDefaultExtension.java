package run.freshr.common.extension;

public interface UnitRedisDefaultExtension<R, ID> {

  void save(R redis);

  Boolean exists(ID id);

  R get(ID id);

  void delete(ID id);

}
