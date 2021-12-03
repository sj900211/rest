package run.freshr.common.extension;

public interface UnitDefaultExtension<E, ID> {

  ID create(E entity);

  Boolean exists(ID id);

  E get(ID id);

  void delete(ID id);

}
