package SoftSquared.PeopleOfDelivery.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_READ_USERS(true, 1010, "회원 전체 정보 조회에 성공하였습니다."),
    SUCCESS_READ_USER(true, 1011, "회원 정보 조회에 성공하였습니다."),
    SUCCESS_POST_USER(true, 1012, "회원가입에 성공하였습니다."),
    SUCCESS_LOGIN(true, 1013, "로그인에 성공하였습니다."),
    SUCCESS_JWT(true, 1014, "JWT 검증에 성공하였습니다."),
    SUCCESS_DELETE_USER(true, 1015, "회원 탈퇴에 성공하였습니다."),
    SUCCESS_PATCH_USER(true, 1016, "회원정보 수정에 성공하였습니다."),
    SUCCESS_READ_SEARCH_USERS(true, 1017, "회원 검색 조회에 성공하였습니다."),
    SUCCESS_READ_STORES(true,1018,"상점 전체 검색 조회에 성공하였습니다."),
    SUCCESS_POST_STORE(true,1019,"상점 추가에 성공하였습니다."),
    SUCCESS_READ_STORE(true,1020,"상점 상세 검색에 성공하였습니다."),
    SUCCESS_READ_DETAIL_STORES(true,1021,"상점 상세 조회에 성공하였습니다."),
    SUCCESS_READ_STORES_BY_ORDER(true,1022,"상점 주문 많은 순 조회에 성공하였습니다."),
    SUCCESS_READ_STORES_BY_DeliveryFeeLowBound(true,1023,"특정 배달료보다 낮은 순 상점 조회에 성공하였습니다."),
    SUCCESS_READ_STORES_BY_DELIVERY_FEE_ASC(true,1024,"낮은 배달료 순서로 모든 상점 조회에 성공하였습니다."),
    SUCCESS_READ_STORES_BY_LOW_BOUND_PRICE_ASC(true,1025,"특정 최소 주문 금액 이상으로 모든 상점 조회에 성공하였습니다."),
    SUCCESS_READ_MENUS(true,1026,"전체 메뉴 조회에 성공하였습니다."),
    SUCCESS_POST_MENU(true, 1017, "메뉴 추가에 성공하였습니다."),
    SUCCESS_READ_MENU(true, 1018, "메뉴 상세 조회에 성공하였습니다."),
    SUCCESS_READ_SHOPPING_BASKET(true, 1019, "회원 장바구니 추가에 성공하였습니다."),
    SUCCESS_READ_SHOPPING_BASKET_TOTAL_PRICE(true, 1020, "회원 장바구니 총 금액 조회에 성공하였습니다."),
    SUCCESS_READ_ORDER(true, 1021, "주문에 성공하였습니다."),
    SUCCESS_READ_COUPON(true, 1022, "쿠폰 조회에  성공하였습니다."),
    SUCCESS_PATCH_COUPON(true, 1023, "쿠폰 갱신에  성공하였습니다."),
    SUCCESS_READ_ORDERLIST_BY_USER(true, 1024, "회원 주문 내역조회에 성공했습니다."),
    SUCCESS_READ_ORDERLIST(true, 1025, "전체 주문 내역조회에 성공했습니다."),
    SUCCESS_READ_ORDER_DETAIL(true, 1026, "주문 내역 상세 조회에 성공했습니다."),
    SUCCESS_DELETE_COUPON(true, 1027, "쿠폰 삭제에 성공했습니다."),
    SUCCESS_UPDATE_MENU(true, 1028, "메뉴 수정에 성공했습니다."),
    SUCCESS_DELETE_MENU(true, 1029, "메뉴 삭제에 성공했습니다."),
    SUCCESS_UPDATE_USER(true, 1030, "회원 프로필 수정에 성공했습니다."),
    SUCCESS_DELETE_ORDER(true, 1031, "주문 삭제에 성공했습니다."),
    SUCCESS_UPDATE_STORE(true, 1032, "상점 수정에 성공했습니다."),
    SUCCESS_DELETE_STORE(true, 1033, "상점 삭제에 성공했습니다."),
    SUCCESS_UPDATE_SHOPPING_BASKET(true, 1034, "장바구니 수정에 성공했습니다."),
    SUCCESS_DELETE_SHOPPING_BASKET(true, 1035, "장바구니 삭제에 성공했습니다."),
    SUCCESS_READ_PAYMENTS(true, 1036, "전체 결제 내역 조회에 성공했습니다."),
    SUCCESS_READ_PAYMENT(true, 1037, "결제 내역 조회에 성공했습니다."),
    SUCCESS_UPDATE_PAYMENT(true, 1038, "결제 내역 수정에 성공했습니다."),
    SUCCESS_DELETE_PAYMENT(true, 1038, "결제 내역 삭제에 성공했습니다."),
    SUCCESS_POST_REVIEW(true, 1039, "리뷰 추가에 성공했습니다."),
    SUCCESS_READ_REVIEW(true, 1040, "리뷰 조회에 성공했습니다."),
    SUCCESS_READ_MY_REVIEW(true, 1041, "내 리뷰 조회에 성공했습니다."),
    SUCCESS_READ_STORE_REVIEW(true, 1042, "가게 리뷰 조회에 성공했습니다."),
    SUCCESS_READ_REVIEWS(true, 1043, "전체 리뷰 조회에 성공했습니다."),
    SUCCESS_UPDATE_REVIEW(true, 1044, "리뷰 수정에 성공했습니다."),
    SUCCESS_DELETE_REVIEW(true, 1045, "리뷰 삭제에 성공했습니다."),
    SUCCESS_LOGOUT(true, 1046, "로그 아웃에 성공했습니다."),
    SUCCESS_REFRESH_JWT(true, 1047, "JWT 갱신에 성공했습니다."),


    // 2000 : Request 오류
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_USERID(false, 2001, "유저 아이디 값을 확인해주세요."),
    EMPTY_JWT(false, 2010, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2011, "유효하지 않은 JWT입니다."),
    EMPTY_EMAIL(false, 2020, "이메일을 입력해주세요."),
    INVALID_EMAIL(false, 2021, "이메일 형식을 확인해주세요."),
    EMPTY_PASSWORD(false, 2030, "비밀번호를 입력해주세요."),
    EMPTY_CONFIRM_PASSWORD(false, 2031, "비밀번호 확인을 입력해주세요."),
    WRONG_PASSWORD(false, 2032, "비밀번호를 다시 입력해주세요."),
    DO_NOT_MATCH_PASSWORD(false, 2033, "비밀번호와 비밀번호확인 값이 일치하지 않습니다."),
    EMPTY_NICKNAME(false, 2040, "닉네임을 입력해주세요."),
    EMPTY_MENU(false, 2041, "최소 한가지 메뉴를 장바구니에 추가해주세요"),
    EMPTY_ORDERLIST(false, 2042, "주문 내역이 비었습니다."),
    EMPTY_PAYMENT(false, 2043, "결제 내역이 비었습니다."),
    EMPTY_REVIEW(false, 2044, "리뷰 내역이 비었습니다."),
    DUPLICATED_REVIEW(false, 2045, "리뷰가 이미 존재합니다."),
    NOT_AUTEHNTICATION(false, 2046, "권한임 없스니다."),
    ALREADY_LOGOUT(false,2047,"이미 로그아웃 처리되었습니다."),
    EXPIRED_JWT(false,2048,"JWT가 만료되었습니다"),
    EXPIRED_REFRESH_JWT(false,2049,"REFRESH JWT가 만료되었습니다"),
    INVAILD_REFRESH_JWT(false,2050,"유효하지 않은 JWT입니다."),
    EMPTY_AUTHENTICATION(false,2051,"권한을 입력하세요."),



    // 3000 : Response 오류
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),
    DUPLICATED_USER(false, 3011, "이미 존재하는 회원입니다."),
    FAILED_TO_GET_USER(false, 3012, "회원 정보 조회에 실패하였습니다."),
    FAILED_TO_POST_USER(false, 3013, "회원가입에 실패하였습니다."),
    FAILED_TO_LOGIN(false, 3014, "로그인에 실패하였습니다."),
    FAILED_TO_DELETE_USER(false, 3015, "회원 탈퇴에 실패하였습니다."),
    FAILED_TO_PATCH_USER(false, 3016, "개인정보 수정에 실패하였습니다."),
    FAILED_TO_GET_STORES(false, 3017, "상점 조회에 실패하였습니다."),
    FAILED_TO_POST_STORE(false, 3018, "상점 가입에 실패하였습니다."),
    FAILED_TO_GET_DETAIL_STORE(false, 3019, "상점 상세 조회에 실패했습니다."),
    FAILED_TO_GET_MENUS(false, 3020, "전체 메뉴 조회에 실패했습니다."),
    FAILED_TO_GET_MENU(false, 3021, "메뉴 조회에 실패했습니다."),
    FAILED_TO_GET_BASKET(false, 3022, "회원 장바구니 조회에 실패했습니다."),
    FAILED_TO_POST_SHOPPING_BASKET(false, 3023, "장바구니 조회에 실패 했습니다."),
    NOT_FOUND_STORE(false, 3030, "존재하지 않는 가게입니다."),
    FAILED_TO_POST_ORDER_DETAIL(false, 3031, "주문 상세 추가에 실패했습니다."),
    FAILED_TO_POST_PAYMENT(false, 3032, "결제에 실패했습니다."),
    FAILED_TO_GET_ORDER(false, 3033, "주문 목록을 가져오는데 실패했습니다."),
    FAILED_TO_POST_ORDER(false, 3034, "주문하는데 실패했습니다."),
    FAILED_TO_GET_ORDER_DETAIL(false, 3035, "주문 상세를 가져오는데 실패했습니다."),
    FAILED_TO_UPDATE_ORDER(false, 3035, "주문을 처리완료하는데 실패했습니다."),
    FAILED_TO_UPDATE_ORDER_DETAIL (false, 3035, "주문 상세를 처리완료하는데 실패했습니다."),
    FAILED_TO_DELETE_BASKET(false, 3035, "장바구니 삭제 실패했습니다."),
    FAILED_TO_UPDATE_USER(false, 3035, "회원 수정에 실패했습니다."),
    FAILED_TO_POST_COUPON(false, 3036, "회원 쿠폰 등록에 실패했습니다."),
    FAILED_TO_GET_COUPON(false, 3037, " 쿠폰 조회에 실패했습니다."),
    FAILED_TO_UPDATE_COUPON(false, 3038, " 쿠폰 갱신에 실패했습니다."),
    FAILED_TO_GET_ORDERS(false, 3039, " 주문 내역 전체 조회에 실패했습니다."),
    FAILED_TO_DELETE_COUPON(false, 3040, "쿠폰 삭제에 실패 했습니다."),
    FAILED_TO_POST_MENU(false, 3041, "메뉴 추가에 실패 했습니다."),
    FAILED_TO_UPDATE_MENU(false, 3042, "메뉴 수정에 실패 했습니다."),
    FAILED_TO_DELETE_MENU(false, 3043, "메뉴 삭제에 실패 했습니다."),
    STOPED_USER(false, 3044, "정지된 회원입니다."),
    FAILED_TO_DELETE_ORDER(false, 3045, "주문 삭제에 실패 했습니다."),
    FAILED_TO_UPDATE_STORE(false, 3046, "가게 수정에 실패 했습니다."),
    FAILED_TO_UPDATE_BASKET(false, 3047, "장바구니 수정에 실패 했습니다."),
    FAILED_TO_GET_PAYMENT(false, 3048, "결제 내역을 가져오는데 실패 했습니다."),
    FAILED_TO_UPDATE_PAYMENT(false, 3049, "결제 내역 수정에 실패 했습니다."),
    FAILED_TO_DELETE_PAYMENT(false, 3050, "결제 내역 삭제에 실패 했습니다."),
    FAILED_TO_POST_REVIEW(false, 3051, "리뷰 추가에 실패 했습니다."),
    FAILED_TO_GET_REVIEW(false, 3052, "리뷰 조회 실패 했습니다."),
    FAILED_TO_GET_REVIEWS(false, 3053, "리뷰 전체 조회에 실패 했습니다."),
    FAILED_TO_UPDATE_REVIEW(false, 3054, "리뷰 수정에 실패 했습니다."),
    FAILED_TO_DELETE_REVIEW(false, 3055, "리뷰 삭제에 실패 했습니다."),
    FAILED_TO_GET_AUTHENTICATION(false, 3057, "권한 획득에 실패했습니다."),
    FAILED_TO_DELETE_STORE(false, 3058, "가게 삭제에 실패했습니다."),


    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다.");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
