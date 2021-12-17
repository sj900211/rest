package run.freshr.common.extension;

public interface UnitDocumentDefaultExtension<D, ID> {

  void save(D document);

  Boolean exists(ID id);

  D get(ID id);

  void delete(ID id);

}
