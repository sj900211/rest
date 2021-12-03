package run.freshr.common.util;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.enumeration.ColumnType.BIGINT;
import static run.freshr.enumeration.ColumnType.BIT;
import static run.freshr.enumeration.ColumnType.BLOB;
import static run.freshr.enumeration.ColumnType.DATE;
import static run.freshr.enumeration.ColumnType.DATETIME;
import static run.freshr.enumeration.ColumnType.DECIMAL;
import static run.freshr.enumeration.ColumnType.DOUBLE;
import static run.freshr.enumeration.ColumnType.FLOAT;
import static run.freshr.enumeration.ColumnType.INT;
import static run.freshr.enumeration.ColumnType.SMALLINT;
import static run.freshr.enumeration.ColumnType.TIME;
import static run.freshr.enumeration.ColumnType.TINYINT;
import static run.freshr.enumeration.ColumnType.UNKNOWN;
import static run.freshr.enumeration.ColumnType.VARCHAR;

import com.querydsl.core.types.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import run.freshr.annotation.ColumnComment;
import run.freshr.common.snippet.PopupFieldsSnippet;
import run.freshr.enumeration.ColumnType;

@Slf4j
public class PrintUtil {

  // Parameter Description 목록
  private final List<ParameterDescriptor> parameterList = new ArrayList<>();
  // Field Description 목록
  private final List<FieldDescriptor> fieldList = new ArrayList<>();
  // Popup Fields Description 목록
  private final List<PopupFieldsSnippet> popupList = new ArrayList<>();

  public PrintUtil() {
  }

  public PrintUtil(Builder builder) {
    log.info("PrintUtil.Constructor");

    this.parameterList.addAll(builder.parameterList);
    this.fieldList.addAll(builder.fieldList);
    this.popupList.addAll(builder.popupList);
  }

  public static Builder builder() {
    log.info("PrintUtil.builder");

    return new Builder();
  }

  public List<ParameterDescriptor> getParameterList() {
    log.info("PrintUtil.getParameterList");

    return parameterList;
  }

  public List<FieldDescriptor> getFieldList() {
    log.info("PrintUtil.getFieldList");

    return fieldList;
  }

  public List<PopupFieldsSnippet> getPopupList() {
    log.info("PrintUtil.getPopupList");

    return popupList;
  }

  // .______    __    __   __   __       _______   _______ .______
  // |   _  \  |  |  |  | |  | |  |     |       \ |   ____||   _  \
  // |  |_)  | |  |  |  | |  | |  |     |  .--.  ||  |__   |  |_)  |
  // |   _  <  |  |  |  | |  | |  |     |  |  |  ||   __|  |      /
  // |  |_)  | |  `--'  | |  | |  `----.|  '--'  ||  |____ |  |\  \----.
  // |______/   \______/  |__| |_______||_______/ |_______|| _| `._____|

  public static class Builder {

    // Parameter Description 목록
    private final List<ParameterDescriptor> parameterList = new ArrayList<>();
    // Field Description 목록
    private final List<FieldDescriptor> fieldList = new ArrayList<>();
    // Popup Fields Description 목록
    private final List<PopupFieldsSnippet> popupList = new ArrayList<>();
    private String prefix = ""; // prefix 문자
    private String prefixDescription = ""; // prefix 설명
    private Boolean prefixOptional = false; // prefix 필수 여부 설정

    public Builder() {
      log.info("PrintUtil.Builder.Constructor");
    }

    public PrintUtil build() {
      log.info("PrintUtil.Builder.build");

      return new PrintUtil(this);
    }

    public Builder prefix(String prefix) {
      log.info("PrintUtil.Builder.prefix");

      this.prefix = prefix;

      return this;
    }

    public Builder prefixDescription(String prefixDescription) {
      log.info("PrintUtil.Builder.prefixDescription");

      this.prefixDescription = prefixDescription;

      return this;
    }

    public Builder prefixOptional() {
      log.info("PrintUtil.Builder.prefixOptional");

      return prefixOptional(true);
    }

    public Builder prefixOptional(Boolean optional) {
      log.info("PrintUtil.Builder.prefixOptional");

      this.prefixOptional = optional;

      return this;
    }

    public Builder clearPrefix() {
      log.info("PrintUtil.Builder.clearPrefix");

      this.prefix = "";

      return this;
    }

    public Builder clearPrefixDescription() {
      log.info("PrintUtil.Builder.clearPrefixDescription");

      this.prefixDescription = "";

      return this;
    }

    public Builder clearOptional() {
      log.info("PrintUtil.Builder.clearOptional");

      this.prefixOptional = false;

      return this;
    }

    public Builder clear() {
      log.info("PrintUtil.Builder.clear");

      this.prefix = "";
      this.prefixDescription = "";
      this.prefixOptional = false;

      return this;
    }

    // .______      ___      .______          ___      .___  ___.  _______ .___________. _______ .______
    // |   _  \    /   \     |   _  \        /   \     |   \/   | |   ____||           ||   ____||   _  \
    // |  |_)  |  /  ^  \    |  |_)  |      /  ^  \    |  \  /  | |  |__   `---|  |----`|  |__   |  |_)  |
    // |   ___/  /  /_\  \   |      /      /  /_\  \   |  |\/|  | |   __|      |  |     |   __|  |      /
    // |  |     /  _____  \  |  |\  \----./  _____  \  |  |  |  | |  |____     |  |     |  |____ |  |\  \----.
    // | _|    /__/     \__\ | _| `._____/__/     \__\ |__|  |__| |_______|    |__|     |_______|| _| `._____|

    public Builder parameter(Path<?>... paths) {
      log.info("PrintUtil.Builder.parameter");

      of(paths).forEach(this::parameter);

      return this;
    }

    public Builder parameter(Path<?> path) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, false, new Attributes.Attribute[0]);
    }

    public Builder parameter(Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, optional, new Attributes.Attribute[0]);
    }

    public Builder parameter(Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(path, false, attributes);
    }

    public Builder parameter(Path<?> path, Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return parameter(
          Optional.of(pathMap.get("name").toString()).orElse(""),
          Optional.of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder parameter(String name, String description) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, false, new Attributes.Attribute[0]);
    }

    public Builder parameter(String name, String description, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, optional, new Attributes.Attribute[0]);
    }

    public Builder parameter(String name, String description, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(name, description, false, attributes);
    }

    public Builder parameter(HashMap<?, ?>... maps) {
      log.info("PrintUtil.Builder.parameter");

      of(maps).forEach(this::parameter);

      return this;
    }

    public Builder parameter(HashMap<?, ?> map) {
      log.info("PrintUtil.Builder.parameter");

      return parameter(map, false);
    }

    public Builder parameter(HashMap<?, ?> map, Boolean optional) {
      log.info("PrintUtil.Builder.parameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          Optional.of(map.get("name").toString()).orElse(""),
          Optional.of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder parameter(HashMap<?, ?> map, String description) {
      log.info("PrintUtil.Builder.parameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          Optional.of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder parameter(String name, String description, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.parameter");

      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    public Builder linkParameter(String groupName, Path<?>... paths) {
      log.info("PrintUtil.Builder.linkParameter");

      of(paths).forEach(this::parameter);

      return this;
    }

    public Builder linkParameter(String groupName, Path<?> path) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, path, false, new Attributes.Attribute[0]);
    }

    public Builder linkParameter(String groupName, Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, path, optional, new Attributes.Attribute[0]);
    }

    public Builder linkParameter(String groupName, Path<?> path,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, path, false, attributes);
    }

    public Builder linkParameter(String groupName, Path<?> path, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return linkParameter(
          groupName,
          Optional.of(pathMap.get("name").toString()).orElse(""),
          Optional.of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder linkParameter(String groupName, String name, String description) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, name, description, false, new Attributes.Attribute[0]);
    }

    public Builder linkParameter(String groupName, String name, String description,
        Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, name, description, optional, new Attributes.Attribute[0]);
    }

    public Builder linkParameter(String groupName, String name, String description,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, name, description, false, attributes);
    }

    public Builder linkParameter(String groupName, HashMap<?, ?>... maps) {
      log.info("PrintUtil.Builder.linkParameter");

      of(maps).forEach(this::parameter);

      return this;
    }

    public Builder linkParameter(String groupName, HashMap<?, ?> map) {
      log.info("PrintUtil.Builder.linkParameter");

      return linkParameter(groupName, map, false);
    }

    public Builder linkParameter(String groupName, HashMap<?, ?> map, Boolean optional) {
      log.info("PrintUtil.Builder.linkParameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          groupName,
          Optional.of(map.get("name").toString()).orElse(""),
          Optional.of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder linkParameter(String groupName, HashMap<?, ?> map, String description) {
      log.info("PrintUtil.Builder.linkParameter");

      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          groupName,
          Optional.of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder linkParameter(String groupName, String name, String description,
        Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkParameter");

      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description("link:popup-" + groupName + "-" + name + "[" + prefixDescription + " "
                  + description + ",role=\"popup\"]")
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    private Builder optional(ParameterDescriptor parameterDescriptor, Boolean optional) {
      log.info("PrintUtil.Builder.optional");

      parameterList.add(optional ? parameterDescriptor.optional() : parameterDescriptor);

      return this;
    }

    //  _______  __   _______  __       _______
    // |   ____||  | |   ____||  |     |       \
    // |  |__   |  | |  |__   |  |     |  .--.  |
    // |   __|  |  | |   __|  |  |     |  |  |  |
    // |  |     |  | |  |____ |  `----.|  '--'  |
    // |__|     |__| |_______||_______||_______/

    public Builder field(Path<?>... paths) {
      log.info("PrintUtil.Builder.field");

      of(paths).forEach(this::field);

      return this;
    }

    public Builder field(Path<?> path) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, false);
    }

    public Builder field(Path<?> path, String comment) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, null, false);
    }

    public Builder field(Path<?> path, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, false);
    }

    public Builder field(Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, optional);
    }

    public Builder field(Path<?> path, String comment, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, null, optional);
    }

    public Builder field(Path<?> path, String comment, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(path, comment, type, false);
    }

    public Builder field(Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, null, false, attributes);
    }

    public Builder field(Path<?> path, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, optional);
    }

    public Builder field(Path<?> path, JsonFieldType type, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(path, null, type, false, attributes);
    }

    public Builder field(Path<?> path, String comment, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return field(
          Optional.of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder field(String name, String description, JsonFieldType type) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, false, new Attributes.Attribute[0]);
    }

    public Builder field(String name, String description, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, optional, new Attributes.Attribute[0]);
    }

    public Builder field(String name, String description, JsonFieldType type,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return field(name, description, type, false, attributes);
    }

    public Builder field(String name, String description, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.field");

      return optional(
          fieldWithPath((hasLength(prefix) ? prefix + "." : "") + name)
              .type(type)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    public Builder linkField(Path<?>... paths) {
      log.info("PrintUtil.Builder.linkField");

      of(paths).forEach(this::linkField);

      return this;
    }

    public Builder linkField(Path<?> path) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, null, false);
    }

    public Builder linkField(Path<?> path, String comment) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, comment, null, false);
    }

    public Builder linkField(Path<?> path, JsonFieldType type) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, type, false);
    }

    public Builder linkField(Path<?> path, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, null, optional);
    }

    public Builder linkField(Path<?> path, String comment, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, comment, null, optional);
    }

    public Builder linkField(Path<?> path, String comment, JsonFieldType type) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, comment, type, false);
    }

    public Builder linkField(Path<?> path, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, null, false, attributes);
    }

    public Builder linkField(Path<?> path, JsonFieldType type, Boolean optional) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, type, optional);
    }

    public Builder linkField(Path<?> path, JsonFieldType type, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return linkField(path, null, type, false, attributes);
    }

    public Builder linkField(Path<?> path, String comment, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return linkField(
          path.getType().getSimpleName(),
          Optional.of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    public Builder linkField(String className, String name, String description, JsonFieldType type,
        Boolean optional, Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.linkField");

      return optional(
          fieldWithPath((hasLength(prefix) ? prefix + "." : "") + name)
              .type(type)
              .description(
                  "link:popup-" + UPPER_CAMEL.to(LOWER_HYPHEN, className) + "[" + prefixDescription
                      + " " + description + ",role=\"popup\"]")
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    private Builder optional(FieldDescriptor fieldDescriptor, Boolean optional) {
      log.info("PrintUtil.Builder.optional");

      fieldList.add(optional ? fieldDescriptor.optional() : fieldDescriptor);
      return this;
    }

    public Builder addField(List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.addField");

      fieldList.addAll(fieldDescriptorList);
      return this;
    }

    public Builder popup(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popup");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath(title).withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    public Builder popupData(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupData");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("data").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    public Builder popupList(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupList");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("list[]").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    public Builder popupPage(String title, List<FieldDescriptor> fieldDescriptorList) {
      log.info("PrintUtil.Builder.popupPage");

      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("page").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    //   ______   ______   .___  ___. .___  ___.   ______   .__   __.
    //  /      | /  __  \  |   \/   | |   \/   |  /  __  \  |  \ |  |
    // |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |  |  | |   \|  |
    // |  |     |  |  |  | |  |\/|  | |  |\/|  | |  |  |  | |  . `  |
    // |  `----.|  `--'  | |  |  |  | |  |  |  | |  `--'  | |  |\   |
    //  \______| \______/  |__|  |__| |__|  |__|  \______/  |__| \__|

    public HashMap<String, Object> pathMap(Path<?> path) {
      log.info("PrintUtil.Builder.pathMap");

      HashMap<String, Object> map = new HashMap<>();
      String qPath = path.toString();
      int qDotPoint = qPath.indexOf(".") + 1;
      String target = qPath.substring(0, qDotPoint);
      String name = qPath.replace(target, "").replace(")", "[]");
      ColumnType columnType = getColumnType(path.getType().getTypeName());
      String description = "";
      String size = columnType.getSize();
      String format = columnType.getFormat();
      JsonFieldType type = getJsonType(columnType);

      ColumnComment columnComment = path.getAnnotatedElement().getAnnotation(ColumnComment.class);
      Column column = path.getAnnotatedElement().getAnnotation(Column.class);

      if (!isNull(columnComment)) {
        description = columnComment.value();
      }

      if (!isNull(column) && path.getType().getTypeName().equals(String.class.getTypeName())) {
        size = Optional.of(column.length()).orElse(0) + " characters";
      }

      map.put("name", name); // 이름
      map.put("description", description); // 설명
      map.put("columnType", columnType); // 컬럼 유형
      map.put("size", size); // 제한 크기
      map.put("format", format); // 규칙
      map.put("type", type); // 유형

      return map;
    }

    private List<Attributes.Attribute> attributeList(HashMap<String, Object> pathMap,
        Attributes.Attribute... attributes) {
      log.info("PrintUtil.Builder.attributeList");

      List<Attributes.Attribute> originList = of(attributes);
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (pathMap.containsKey("size") && originList.stream()
          .noneMatch(attribute -> attribute.getKey().equals("size"))) {
        attributeList.add(key("size").value(pathMap.get("size")));
      }

      if (pathMap.containsKey("format") && originList.stream()
          .noneMatch(attribute -> attribute.getKey().equals("format"))) {
        attributeList.add(key("format").value(pathMap.get("format")));
      }

      return attributeList;
    }

    private ColumnType getColumnType(String type) {
      log.info("PrintUtil.Builder.getColumnType");

      ColumnType columnType;

      switch (type) {
        case "java.lang.Float":
          columnType = FLOAT;
          break;

        case "java.lang.Double":
          columnType = DOUBLE;
          break;

        case "java.lang.BigDecimal":
          columnType = DECIMAL;
          break;

        case "java.lang.Byte":
          columnType = TINYINT;
          break;

        case "java.lang.Short":
          columnType = SMALLINT;
          break;

        case "java.lang.String":
          columnType = VARCHAR;
          break;

        case "java.lang.Long":
          columnType = BIGINT;
          break;

        case "java.lang.Integer":
          columnType = INT;
          break;

        case "java.lang.Boolean":
          columnType = BIT;
          break;

        case "java.time.LocalDate":
          columnType = DATE;
          break;

        case "java.time.LocalDateTime":
          columnType = DATETIME;
          break;

        case "java.time.LocalTime":
          columnType = TIME;
          break;

        case "byte[]":
          columnType = BLOB;
          break;

        default:
          columnType = UNKNOWN;
          break;
      }

      return columnType;
    }

    private JsonFieldType getJsonType(ColumnType columnType) {
      log.info("PrintUtil.Builder.getJsonType");

      JsonFieldType jsonFieldType;

      switch (columnType) {
        case TINYINT:
        case BIT:
          jsonFieldType = BOOLEAN;
          break;

        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case SMALLINT:
        case BIGINT:
        case INT:
          jsonFieldType = NUMBER;
          break;

        case DATE:
        case TIME:
        case DATETIME:
        case VARCHAR:
        case LONGTEXT:
        case BLOB:
        default:
          jsonFieldType = STRING;
          break;
      }

      return jsonFieldType;
    }

  }

}
