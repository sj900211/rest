package run.freshr.domain.community;

import static run.freshr.domain.community.entity.QBoard.board;
import static run.freshr.domain.mapping.entity.QBoardAttachMapping.boardAttachMapping;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.common.ResponseDocs;
import run.freshr.domain.community.vo.ECommunitySearch;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

public class BoardDocs {

  public static class Request {

    public static List<FieldDescriptor> createBoard() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .field(board.title, board.content)

          .prefixOptional()

          .field(board.attachList.any().attach.id, board.attachList.any().sort)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getBoardPage() {
      return PrintUtil
          .builder()

          .parameter(ECommunitySearch.page, ECommunitySearch.cpp)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBoard() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(board.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> updateBoardPath() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(board.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updateBoard() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .field(board.title, board.content)

          .prefixOptional()

          .field(board.attachList.any().attach.id, board.attachList.any().sort)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removeBoard() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(board.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBoardAttachMappingList() {
      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(board.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createBoard() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("게시글")

          .field(board.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBoardPage() {
      return ResponseDocs
          .Response
          .page()

          .prefixDescription("게시글")

          .field(board.id, board.title, board.content, board.hit, board.createDt, board.updateDt,
              board.creator.id, board.creator.username, board.creator.name)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBoard() {
      return ResponseDocs
          .Response
          .data()

          .prefixDescription("게시글")

          .field(board.id, board.title, board.content, board.hit, board.createDt, board.updateDt,
              board.creator.id, board.creator.username, board.creator.name)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBoardAttachMappingList() {
      return ResponseDocs
          .Response
          .list()

          .prefixOptional()
          .prefixDescription("게시글 첨부파일")

          .field(
              boardAttachMapping.sort,
              boardAttachMapping.attach.id,
              boardAttachMapping.attach.contentType,
              boardAttachMapping.attach.filename,
              boardAttachMapping.attach.size,
              boardAttachMapping.attach.alt,
              boardAttachMapping.attach.title,
              boardAttachMapping.attach.createDt,
              boardAttachMapping.attach.updateDt
          )

          .field("attach.url", "리소스 URL <만료 시간 1 분>", STRING)

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
