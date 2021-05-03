package run.freshr.domain.auth.repository;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static run.freshr.domain.auth.entity.QAccount.account;
import static org.springframework.util.StringUtils.hasLength;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.user.vo.UserSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * The Class Account repository.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 사용자 계정 관리 Repository Impl
 * @since 2021. 3. 16. 오후 2:26:14
 */
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

  /**
   * The Query factory
   */
  private final JPAQueryFactory queryFactory;

  /**
   * Gets page.
   *
   * @param search the search
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:26:14
   */
  @Override
  public Page<Account> getPage(UserSearch search) {
    JPAQuery<Account> query = queryFactory
        .selectFrom(account)
        .where(account.delFlag.isFalse(), account.useFlag.isTrue());

    // 등록 날짜 시간 기간 조회 - by 시작
    LocalDateTime startDt = search.getSdt();

    if (!isNull(startDt)) {
      query.where(account.createDt.goe(startDt));
    }

    LocalDateTime endDt = search.getEdt();

    // 등록 날짜 시간 기간 조회 - by 종료
    if (!isNull(endDt)) {
      query.where(account.createDt.loe(endDt));
    }

    // 자연어 검색
    String word = search.getWord();

    if (hasLength(word)) { // 검색어가 있을 경우
      String key = ofNullable(search.getKey()).orElse("");

      // 고유 아이디로 검색
      if (key.equalsIgnoreCase("username")) {
        query.where(account.username.containsIgnoreCase(word));
      }

      // 이름으로 검색
      if (key.equalsIgnoreCase("name")) {
        query.where(account.name.containsIgnoreCase(word));
      }

      // 전체 검색
      if (!hasLength(key)) {
        query.where(account.username.containsIgnoreCase(word)
            .or(account.name.containsIgnoreCase(word)));
      }
    }

    // 정렬
    String orderTarget = ofNullable(search.getOtarget()).orElse("");
    String orderType = ofNullable(search.getOtype()).orElse(Order.DESC.name());

    if (orderTarget.equalsIgnoreCase("username")) { // 고유 아이디로 정렬
      query.orderBy(new OrderSpecifier<>(
          orderType.contentEquals("desc") ? Order.DESC : Order.ASC, account.username
      ));
    }

    if (orderTarget.equalsIgnoreCase("name")) { // 이름으로 정렬
      query.orderBy(new OrderSpecifier<>(
          orderType.contentEquals("desc") ? Order.DESC : Order.ASC, account.name
      ));
    }

    if (orderTarget.equalsIgnoreCase("create")) { // 등록 날짜 시간 기준으로 정렬
      query.orderBy(new OrderSpecifier<>(
          orderType.contentEquals("desc") ? Order.DESC : Order.ASC, account.createDt
      ));
    }

    query.orderBy(account.id.desc()); // 기본 정렬 조건 추가

    // 페이징 객체 생성
    PageRequest pageRequest = PageRequest.of(search.getPage(), search.getCpp());

    // 페이징 처리
    query.offset(pageRequest.getOffset())
        .limit(pageRequest.getPageSize());

    // 쿼리 실행
    QueryResults<Account> result = query.fetchResults();

    // 페이지 객체로 반환
    return new PageImpl<>(result.getResults(), pageRequest, result.getTotal());
  }

}
