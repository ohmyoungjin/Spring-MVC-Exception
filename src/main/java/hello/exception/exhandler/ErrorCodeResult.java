package hello.exception.exhandler;

//enum 사용으로 response 응답에 대한 정보를 넘겨준다.
//enum은 annotation을 따로 달 수 없어서
// class로 Vo를 만들어서 쓰는 게 조금 더 효율적으로 보이는데 더 공부해봐야 할 것 같다.
public enum ErrorCodeResult {
    OK("200", "OK"),
    BAD_REQUEST("400", "BAD_REQUEST"),
    NOT_FOUND("404", "NOT_FOUND"),
    INTERNAL_SERER_ERROR("500", "INTERNAL_SERVER_ERROR");
    private String code;
    private String message;

    ErrorCodeResult(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }






}
