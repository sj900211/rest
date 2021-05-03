package run.freshr.common.extension;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Response extension.
 *
 * @author [류성재]
 * @implNote 반환 DTO 에서 상속받는 Base DTO
 * @since 2021. 2. 24. 오후 5:52:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExtension {

  /**
   * 일련 번호
   */
  protected Long id;

  /**
   * 등록 날짜 시간
   */
  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  protected LocalDateTime createDt;

  /**
   * 마지막 수정 날짜 시간
   */
  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  protected LocalDateTime updateDt;

}
