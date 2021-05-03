package run.freshr.common.util;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
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
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.util.StringUtils.hasLength;

import com.querydsl.core.types.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import run.freshr.annotation.ColumnComment;
import run.freshr.common.snippet.PopupFieldsSnippet;
import run.freshr.enumeration.ColumnType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.snippet.Attributes;

/**
 * The Class Print util.
 *
 * @author [류성재]
 * @implNote Rest Docs 작성 유틸
 * @since 2021. 3. 16. 오후 3:14:38
 */
public class PrintUtil {

  /**
   * The Parameter li
   */
  private final List<ParameterDescriptor> parameterList = new ArrayList<>(); // Parameter Description 목록
  /**
   * The Field list
   */
  private final List<FieldDescriptor> fieldList = new ArrayList<>(); // Field Description 목록
  /**
   * The Popup list
   */
  private final List<PopupFieldsSnippet> popupList = new ArrayList<>(); // Popup Fields Description 목록

  /**
   * Instantiates a new Print util.
   *
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public PrintUtil() {
  }

  /**
   * Instantiates a new Print util.
   *
   * @param builder the builder
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public PrintUtil(Builder builder) {
    this.parameterList.addAll(builder.parameterList);
    this.fieldList.addAll(builder.fieldList);
    this.popupList.addAll(builder.popupList);
  }

  /**
   * Builder builder.
   *
   * @return the builder
   * @author [류성재]
   * @implNote 빌더 생성 메서드
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Gets parameter list.
   *
   * @return the parameter list
   * @author [류성재]
   * @implNote Parameter Description 목록 Getter
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public List<ParameterDescriptor> getParameterList() {
    return parameterList;
  }

  /**
   * Gets field list.
   *
   * @return the field list
   * @author [류성재]
   * @implNote Field Description 목록 Getter
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public List<FieldDescriptor> getFieldList() {
    return fieldList;
  }

  /**
   * Gets popup list.
   *
   * @return the popup list
   * @author [류성재]
   * @implNote Popup Fields Description 목록 Getter
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public List<PopupFieldsSnippet> getPopupList() {
    return popupList;
  }

  // .______    __    __   __   __       _______   _______ .______
  // |   _  \  |  |  |  | |  | |  |     |       \ |   ____||   _  \
  // |  |_)  | |  |  |  | |  | |  |     |  .--.  ||  |__   |  |_)  |
  // |   _  <  |  |  |  | |  | |  |     |  |  |  ||   __|  |      /
  // |  |_)  | |  `--'  | |  | |  `----.|  '--'  ||  |____ |  |\  \----.
  // |______/   \______/  |__| |_______||_______/ |_______|| _| `._____|

  /**
   * The Class Builder.
   *
   * @author [류성재]
   * @implNote 빌더 Class
   * @since 2021. 3. 16. 오후 3:14:38
   */
  public static class Builder {

    /**
     * The Parameter li
     */
    private final List<ParameterDescriptor> parameterList = new ArrayList<>(); // Parameter Description 목록
    /**
     * The Field list
     */
    private final List<FieldDescriptor> fieldList = new ArrayList<>(); // Field Description 목록
    /**
     * The Popup list
     */
    private final List<PopupFieldsSnippet> popupList = new ArrayList<>(); // Popup Fields Description 목록
    /**
     * The Prefix
     */
    private String prefix = ""; // prefix 문자
    /**
     * The Prefix descr
     */
    private String prefixDescription = ""; // prefix 설명
    /**
     * The Prefix optio
     */
    private Boolean prefixOptional = false; // prefix 필수 여부 설정

    /**
     * Instantiates a new Builder.
     *
     * @author [류성재]
     * @implNote 생성자
     * @since 2021. 3. 16. 오후 3:14:38
     */
    public Builder() {
    }

    /**
     * Build print util.
     *
     * @return the print util
     * @author [류성재]
     * @implNote 설정한 내용을 적용 후 반환
     * @since 2021. 3. 16. 오후 3:14:38
     */
    public PrintUtil build() {
      return new PrintUtil(this);
    }

    /**
     * Prefix builder.
     *
     * @param prefix the prefix
     * @return the builder
     * @author [류성재]
     * @implNote prefix 문자 등록
     * @since 2021. 3. 16. 오후 3:14:38
     */
    public Builder prefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    /**
     * Prefix description builder.
     *
     * @param prefixDescription the prefix description
     * @return the builder
     * @author [류성재]
     * @implNote prefix 설명 등록
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder prefixDescription(String prefixDescription) {
      this.prefixDescription = prefixDescription;
      return this;
    }

    /**
     * Prefix optional builder.
     *
     * @return the builder
     * @author [류성재]
     * @implNote prefix 필수 여부 설정
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder prefixOptional() {
      return prefixOptional(true);
    }

    /**
     * Prefix optional builder.
     *
     * @param optional the optional
     * @return the builder
     * @author [류성재]
     * @implNote prefix 필수 여부 설정
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder prefixOptional(Boolean optional) {
      this.prefixOptional = optional;
      return this;
    }

    /**
     * Clear prefix builder.
     *
     * @return the builder
     * @author [류성재]
     * @implNote prefix 문자 제거
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder clearPrefix() {
      this.prefix = "";
      return this;
    }

    /**
     * Clear prefix description builder.
     *
     * @return the builder
     * @author [류성재]
     * @implNote prefix 설명 제거
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder clearPrefixDescription() {
      this.prefixDescription = "";
      return this;
    }

    /**
     * Clear optional builder.
     *
     * @return the builder
     * @author [류성재]
     * @implNote prefix 필수 여부 초기화
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder clearOptional() {
      this.prefixOptional = false;
      return this;
    }

    /**
     * Clear builder.
     *
     * @return the builder
     * @author [류성재]
     * @implNote prefix 설정 전체 제거 및 초기화
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder clear() {
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

    /**
     * Parameter builder.
     *
     * @param paths QueryDSL 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(Path<?>... paths) {
      Arrays.asList(paths).forEach(this::parameter);
      return this;
    }

    /**
     * Parameter builder.
     *
     * @param path QueryDSL 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(Path<?> path) {
      return parameter(path, false, new Attributes.Attribute[0]);
    }

    /**
     * Parameter builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가 for QueryDSL 컴파일 객체
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(Path<?> path, Boolean optional) {
      return parameter(path, optional, new Attributes.Attribute[0]);
    }

    /**
     * Parameter builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(Path<?> path, Attributes.Attribute... attributes) {
      return parameter(path, false, attributes);
    }

    /**
     * Parameter builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param optional   필수 여부
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(Path<?> path, Boolean optional, Attributes.Attribute... attributes) {
      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return parameter(
          of(pathMap.get("name").toString()).orElse(""),
          of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter builder.
     *
     * @param name        이름
     * @param description 설명
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(String name, String description) {
      return parameter(name, description, false, new Attributes.Attribute[0]);
    }

    /**
     * Parameter builder.
     *
     * @param name        이름
     * @param description 설명
     * @param optional    필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(String name, String description, Boolean optional) {
      return parameter(name, description, optional, new Attributes.Attribute[0]);
    }

    /**
     * Parameter builder.
     *
     * @param name        이름
     * @param description 설명
     * @param attributes  속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(String name, String description, Attributes.Attribute... attributes) {
      return parameter(name, description, false, attributes);
    }

    /**
     * Parameter builder.
     *
     * @param maps SearchComment 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(HashMap<?, ?>... maps) {
      Arrays.asList(maps).forEach(this::parameter);
      return this;
    }

    /**
     * Parameter builder.
     *
     * @param map SearchComment 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:39
     */
    public Builder parameter(HashMap<?, ?> map) {
      return parameter(map, false);
    }

    /**
     * Parameter builder.
     *
     * @param map      SearchComment 컴파일 객체
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder parameter(HashMap<?, ?> map, Boolean optional) {
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          of(map.get("name").toString()).orElse(""),
          of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter builder.
     *
     * @param map         SearchComment 컴파일 객체
     * @param description 설명
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder parameter(HashMap<?, ?> map, String description) {
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (!hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return parameter(
          of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Parameter builder.
     *
     * @param name        the name
     * @param description the description
     * @param optional    the optional
     * @param attributes  the attributes
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가 오버로딩 parameter 메서드들의 마지막 지점
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder parameter(String name, String description, Boolean optional,
        Attributes.Attribute... attributes) {
      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param paths     QueryDSL 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, Path<?>... paths) {
      Arrays.asList(paths).forEach(this::parameter);
      return this;
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param path      QueryDSL 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, Path<?> path) {
      return linkParameter(groupName, path, false, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param path      QueryDSL 컴파일 객체
     * @param optional  필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가 for QueryDSL 컴파일 객체
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, Path<?> path, Boolean optional) {
      return linkParameter(groupName, path, optional, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName  팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param path       QueryDSL 컴파일 객체
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, Path<?> path,
        Attributes.Attribute... attributes) {
      return linkParameter(groupName, path, false, attributes);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName  팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param path       QueryDSL 컴파일 객체
     * @param optional   필수 여부
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, Path<?> path, Boolean optional,
        Attributes.Attribute... attributes) {
      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);

      return linkParameter(
          groupName,
          of(pathMap.get("name").toString()).orElse(""),
          of(pathMap.get("description").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter builder.
     *
     * @param groupName   팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param name        이름
     * @param description 설명
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, String name, String description) {
      return linkParameter(groupName, name, description, false, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName   팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param name        이름
     * @param description 설명
     * @param optional    필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, String name, String description,
        Boolean optional) {
      return linkParameter(groupName, name, description, optional, new Attributes.Attribute[0]);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName   팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param name        이름
     * @param description 설명
     * @param attributes  속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, String name, String description,
        Attributes.Attribute... attributes) {
      return linkParameter(groupName, name, description, false, attributes);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param maps      SearchComment 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, HashMap<?, ?>... maps) {
      Arrays.asList(maps).forEach(this::parameter);
      return this;
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param map       SearchComment 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, HashMap<?, ?> map) {
      return linkParameter(groupName, map, false);
    }

    /**
     * Link parameter builder.
     *
     * @param groupName 팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param map       SearchComment 컴파일 객체
     * @param optional  필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:40
     */
    public Builder linkParameter(String groupName, HashMap<?, ?> map, Boolean optional) {
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          groupName,
          of(map.get("name").toString()).orElse(""),
          of(map.get("comment").toString()).orElse(""),
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter builder.
     *
     * @param groupName   팝업 그룹 이름 - link 가 중복되지 않기위한 prefix 문자
     * @param map         SearchComment 컴파일 객체
     * @param description 설명
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder linkParameter(String groupName, HashMap<?, ?> map, String description) {
      List<Attributes.Attribute> attributeList = new ArrayList<>();

      if (hasLength(map.get("format").toString())) {
        attributeList.add(key("format").value(map.get("format")));
      }

      return linkParameter(
          groupName,
          of(map.get("name").toString()).orElse(""),
          description,
          prefixOptional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link parameter builder.
     *
     * @param groupName   the group name
     * @param name        the name
     * @param description the description
     * @param optional    the optional
     * @param attributes  the attributes
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 추가 오버로딩 linkParameter 메서드들의 마지막 지점
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder linkParameter(String groupName, String name, String description,
        Boolean optional, Attributes.Attribute... attributes) {
      return optional(
          parameterWithName((hasLength(prefix) ? prefix + "." : "") + name)
              .description("link:popup-" + groupName + "-" + name + "[" + prefixDescription + " "
                  + description + ",role=\"popup\"]")
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Optional builder.
     *
     * @param parameterDescriptor the parameter descriptor
     * @param optional            the optional
     * @return the builder
     * @author [류성재]
     * @implNote Parameter Description 필수 여부 설정
     * @since 2021. 3. 16. 오후 3:14:41
     */
    private Builder optional(ParameterDescriptor parameterDescriptor, Boolean optional) {
      parameterList.add(optional ? parameterDescriptor.optional() : parameterDescriptor);
      return this;
    }

    //  _______  __   _______  __       _______
    // |   ____||  | |   ____||  |     |       \
    // |  |__   |  | |  |__   |  |     |  .--.  |
    // |   __|  |  | |   __|  |  |     |  |  |  |
    // |  |     |  | |  |____ |  `----.|  '--'  |
    // |__|     |__| |_______||_______||_______/

    /**
     * Field builder.
     *
     * @param paths QueryDSL 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?>... paths) {
      Arrays.asList(paths).forEach(this::field);
      return this;
    }

    /**
     * Field builder.
     *
     * @param path QueryDSL 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path) {
      return field(path, null, null, false);
    }

    /**
     * Field builder.
     *
     * @param path    QueryDSL 컴파일 객체
     * @param comment 설명
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, String comment) {
      return field(path, comment, null, false);
    }

    /**
     * Field builder.
     *
     * @param path QueryDSL 컴파일 객체
     * @param type 유형
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, JsonFieldType type) {
      return field(path, null, type, false);
    }

    /**
     * Field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, Boolean optional) {
      return field(path, null, null, optional);
    }

    /**
     * Field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param comment  설명
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, String comment, Boolean optional) {
      return field(path, comment, null, optional);
    }

    /**
     * Field builder.
     *
     * @param path    QueryDSL 컴파일 객체
     * @param comment 설명
     * @param type    유형
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, String comment, JsonFieldType type) {
      return field(path, comment, type, false);
    }

    /**
     * Field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, Attributes.Attribute... attributes) {
      return field(path, null, null, false, attributes);
    }

    /**
     * Field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param type     유형
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, JsonFieldType type, Boolean optional) {
      return field(path, null, type, optional);
    }

    /**
     * Field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param type       유형
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, JsonFieldType type, Attributes.Attribute... attributes) {
      return field(path, null, type, false, attributes);
    }

    /**
     * Field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param comment    설명
     * @param type       유형
     * @param optional   필수 여부
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(Path<?> path, String comment, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return field(
          of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Field builder.
     *
     * @param name        이름
     * @param description 설명
     * @param type        유형
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(String name, String description, JsonFieldType type) {
      return field(name, description, type, false, new Attributes.Attribute[0]);
    }

    /**
     * Field builder.
     *
     * @param name        이름
     * @param description 설명
     * @param type        유형
     * @param optional    필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(String name, String description, JsonFieldType type, Boolean optional) {
      return field(name, description, type, optional, new Attributes.Attribute[0]);
    }

    /**
     * Field builder.
     *
     * @param name        이름
     * @param description 설명
     * @param type        유형
     * @param attributes  속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:41
     */
    public Builder field(String name, String description, JsonFieldType type,
        Attributes.Attribute... attributes) {
      return field(name, description, type, false, attributes);
    }

    /**
     * Field builder.
     *
     * @param name        the name
     * @param description the description
     * @param type        the type
     * @param optional    the optional
     * @param attributes  the attributes
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가 오버로딩 field 메서드들의 마지막 지점
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder field(String name, String description, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      return optional(
          fieldWithPath((hasLength(prefix) ? prefix + "." : "") + name)
              .type(type)
              .description(prefixDescription + " " + description)
              .attributes(attributes),
          prefixOptional || optional
      );
    }

    /**
     * Link field builder.
     *
     * @param paths QueryDSL 컴파일 객체 - Multiple
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?>... paths) {
      Arrays.asList(paths).forEach(this::linkField);
      return this;
    }

    /**
     * Link field builder.
     *
     * @param path QueryDSL 컴파일 객체 - Single
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path) {
      return linkField(path, null, null, false);
    }

    /**
     * Link field builder.
     *
     * @param path    QueryDSL 컴파일 객체
     * @param comment 설명
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, String comment) {
      return linkField(path, comment, null, false);
    }

    /**
     * Link field builder.
     *
     * @param path QueryDSL 컴파일 객체
     * @param type 유형
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, JsonFieldType type) {
      return linkField(path, null, type, false);
    }

    /**
     * Link field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, Boolean optional) {
      return linkField(path, null, null, optional);
    }

    /**
     * Link field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param comment  설명
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, String comment, Boolean optional) {
      return linkField(path, comment, null, optional);
    }

    /**
     * Link field builder.
     *
     * @param path    QueryDSL 컴파일 객체
     * @param comment 설명
     * @param type    유형
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, String comment, JsonFieldType type) {
      return linkField(path, comment, type, false);
    }

    /**
     * Link field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, Attributes.Attribute... attributes) {
      return linkField(path, null, null, false, attributes);
    }

    /**
     * Link field builder.
     *
     * @param path     QueryDSL 컴파일 객체
     * @param type     유형
     * @param optional 필수 여부
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, JsonFieldType type, Boolean optional) {
      return linkField(path, null, type, optional);
    }

    /**
     * Link field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param type       유형
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, JsonFieldType type, Attributes.Attribute... attributes) {
      return linkField(path, null, type, false, attributes);
    }

    /**
     * Link field builder.
     *
     * @param path       QueryDSL 컴파일 객체
     * @param comment    설명
     * @param type       유형
     * @param optional   필수 여부
     * @param attributes 속성 설정
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(Path<?> path, String comment, JsonFieldType type, Boolean optional,
        Attributes.Attribute... attributes) {
      HashMap<String, Object> pathMap = pathMap(path);
      List<Attributes.Attribute> attributeList = attributeList(pathMap, attributes);
      JsonFieldType jsonFieldType = isNull(type) ? (JsonFieldType) pathMap.get("type") : type;
      String description = ofNullable(comment).orElse(pathMap.get("description").toString());

      return linkField(
          path.getType().getSimpleName(),
          of(pathMap.get("name").toString()).orElse(""),
          description,
          jsonFieldType,
          optional,
          attributeList.toArray(new Attributes.Attribute[0])
      );
    }

    /**
     * Link field builder.
     *
     * @param className   the class name
     * @param name        the name
     * @param description the description
     * @param type        the type
     * @param optional    the optional
     * @param attributes  the attributes
     * @return the builder
     * @author [류성재]
     * @implNote Field Description Link 추가 오버로딩 linkField 메서드들의 마지막 지점
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder linkField(String className, String name, String description, JsonFieldType type,
        Boolean optional, Attributes.Attribute... attributes) {
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

    /**
     * Optional builder.
     *
     * @param fieldDescriptor the field descriptor
     * @param optional        the optional
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 필수 여부 설정
     * @since 2021. 3. 16. 오후 3:14:42
     */
    private Builder optional(FieldDescriptor fieldDescriptor, Boolean optional) {
      fieldList.add(optional ? fieldDescriptor.optional() : fieldDescriptor);
      return this;
    }

    /**
     * Add field builder.
     *
     * @param fieldDescriptorList Field Description 목록
     * @return the builder
     * @author [류성재]
     * @implNote Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder addField(List<FieldDescriptor> fieldDescriptorList) {
      fieldList.addAll(fieldDescriptorList);
      return this;
    }

    /**
     * Popup builder.
     *
     * @param title               문서 타이틀
     * @param fieldDescriptorList Field Description 목록
     * @return the builder
     * @author [류성재]
     * @implNote Popup Field Description 추가
     * @since 2021. 3. 16. 오후 3:14:42
     */
    public Builder popup(String title, List<FieldDescriptor> fieldDescriptorList) {
      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath(title).withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup data builder.
     *
     * @param title               문서 타이틀
     * @param fieldDescriptorList Field Description 목록
     * @return the builder
     * @author [류성재]
     * @implNote Popup Field Description 추가 - data 객체 하위 가상의 title 객체가 대상일 경우
     * @since 2021. 3. 16. 오후 3:14:43
     */
    public Builder popupData(String title, List<FieldDescriptor> fieldDescriptorList) {
      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("data").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup list builder.
     *
     * @param title               문서 타이틀
     * @param fieldDescriptorList Field Description 목록
     * @return the builder
     * @author [류성재]
     * @implNote Popup Field Description 추가 - list 객체 하위 가상의 title 객체가 대상일 경우
     * @since 2021. 3. 16. 오후 3:14:43
     */
    public Builder popupList(String title, List<FieldDescriptor> fieldDescriptorList) {
      popupList.add(new PopupFieldsSnippet(
          "popup",
          beneathPath("list[]").withSubsectionId(title),
          fieldDescriptorList,
          attributes(key("title").value(title)),
          true
      ));
      return this;
    }

    /**
     * Popup page builder.
     *
     * @param title               문서 타이틀
     * @param fieldDescriptorList Field Description 목록
     * @return the builder
     * @author [류성재]
     * @implNote Popup Field Description 추가 - page 객체 하위 가상의 title 객체가 대상일 경우
     * @since 2021. 3. 16. 오후 3:14:43
     */
    public Builder popupPage(String title, List<FieldDescriptor> fieldDescriptorList) {
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

    /**
     * Path map hash map.
     *
     * @param path QueryDSL 컴파일 객체
     * @return the hash map
     * @author [류성재]
     * @implNote Path 객체 정보를 Map 으로 변환
     * @since 2021. 3. 16. 오후 3:14:43
     */
    public HashMap<String, Object> pathMap(Path<?> path) {
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
        size = of(column.length()).orElse(0) + " characters";
      }

      map.put("name", name); // 이름
      map.put("description", description); // 설명
      map.put("columnType", columnType); // 컬럼 유형
      map.put("size", size); // 제한 크기
      map.put("format", format); // 규칙
      map.put("type", type); // 유형

      return map;
    }

    /**
     * Attribute list list.
     *
     * @param pathMap    SearchComment 컴파일 객체 - Multiple
     * @param attributes 속성 설정
     * @return the list
     * @author [류성재]
     * @implNote 객체 정보를 Map 으로 변환
     * @since 2021. 3. 16. 오후 3:14:43
     */
    private List<Attributes.Attribute> attributeList(HashMap<String, Object> pathMap,
        Attributes.Attribute... attributes) {
      List<Attributes.Attribute> originList = Arrays.asList(attributes);
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

    /**
     * Gets column type.
     *
     * @param type the type
     * @return the column type
     * @author [류성재]
     * @implNote 컬럼 유형 매칭 및 반환
     * @since 2021. 3. 16. 오후 3:14:43
     */
    private ColumnType getColumnType(String type) {
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

    /**
     * Gets json type.
     *
     * @param columnType the column type
     * @return the json type
     * @author [류성재]
     * @implNote Json 유형 매칭 및 반환
     * @since 2021. 3. 16. 오후 3:14:43
     */
    private JsonFieldType getJsonType(ColumnType columnType) {
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
