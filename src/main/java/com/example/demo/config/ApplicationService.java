package com.example.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class ApplicationService {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String AJAX_RESULT_TEXT = "httpCode";

    // LINE :: AJAX 결과 코드 ====================================================================================================================================================
    public static final String AJAX_RESULT_SUCCESS = "200";         // 성공
    public static final String AJAX_RESULT_DUPLICATE = "300";       // 중복
    public static final String AJAX_RESULT_NODATA = "400";          // 데이터 없음
    public static final String AJAX_RESULT_ILLEGAL_STATE = "401";   // 유효하지 않은 접근
    public static final String AJAX_RESULT_FAIL = "500";            // 실패
    public static final String AJAX_RESULT_OVERFLOW = "999";        // 다중 행 리턴
    public static final String AJAX_RESULT_ALREADY = "600";         // 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_PAY_ALREADY = "601";     // 결제 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_EDU_ALREADY = "602";     // 교육신청 이미 처리되어 있는 경우
    public static final String AJAX_RESULT_PAY_WAIT = "603";        // 결제 대기
    public static final String AJAX_RESULT_LOGIN_BLOCK = "LB";        // 결제 대기

    public static final String AJAX_RESULT_FILE_UPLOADFAIL = "FUF";
    public static final String AJAX_RESULT_FILE_EXTMISSMATCH = "FEM";
    public static final String AJAX_RESULT_FILE_IOEXCEPTION = "FIO";
    public static final String AJAX_RESULT_FILE_TOOMANYFILES = "TMF";
    public static final String AJAX_RESULT_FILE_FILEALREADYLIMITED = "FAL";

    public static final String AJAX_RESULT_UNSUPPORTEDENCODING = "USD";
    public static final String AJAX_RESULT_ENCODER = "ECD";
    public static final String AJAX_RESULT_NOSUCHALGORITHM = "NSA";
    public static final String AJAX_RESULT_INVALIDKEY = "IVK";
    public static final String AJAX_RESULT_INVALIDALGORITHMPARAMETER = "IVA";
    public static final String AJAX_RESULT_NOSUCHPADDING = "NSP";
    public static final String AJAX_RESULT_BADPADDING = "BPD";
    public static final String AJAX_RESULT_ILLEGALBLOCKSIZE = "IBS";
    // LINE :: AJAX 결과 코드 ====================================================================================================================================================
//
//    // LINE :: SESSION KEY 값 ====================================================================================================================================================
//    public static final String HOSPITAL_ADMIN_SESSION_ID =  "hospital_admin_session_id";
//    public static final String HOSPITAL_ADMIN_SESSION_IDX =  "hospital_admin_session_idx";
//    public static final String HOSPITAL_ADMIN_SESSION_NAME =  "hospital_admin_session_name";
//    public static final String HOSPITAL_ADMIN_SESSION_AUTH =  "hospital_admin_session_auth";
//    public static final String HOSPITAL_ADMIN_SESSION_GUBUN = "hospital_admin_session_gubun";
//
//    public static final String EDITOR_IMG_IDXS = "editor_img_idxs";
//    public static final String HOSPITAL_ADMIN_SESSION_SIDOCODE = "hospital_admin_session_sidoCode";
//    public static final String HOSPITAL_ADMIN_SESSION_SIDOPAGE = "hospital_admin_session_sidoPage";
//    public static final String HOSPITAL_ADMIN_SESSION_SIDONAME = "hospital_admin_session_sidoName";
//    // LINE :: SESSION KEY 값 ====================================================================================================================================================

    // LINE :: SESSION 데이터 처리 ====================================================================================================================================================

    @Autowired
    private HttpServletRequest request;

    private HttpSession getSession() {
        return request.getSession(true);
    }
//
//    public void setHospitalAdminSessionId(String id) {
//        getSession().setAttribute(HOSPITAL_ADMIN_SESSION_ID, id);
//    }
//
//    public void setHospitalAdminSessionIdx(Long idx) {
//        getSession().setAttribute(HOSPITAL_ADMIN_SESSION_IDX, idx);
//    }
//
//    public void setHospitalAdminSessionName(String name) {
//        getSession().setAttribute(HOSPITAL_ADMIN_SESSION_NAME, name);
//    }
//
//    public void setHospitalAdminSessionAuth(Long auth) {
//        getSession().setAttribute(HOSPITAL_ADMIN_SESSION_AUTH, auth);
//    }
//
//    public void setHospitalAdminSessionGubun(String gubun) {
//        getSession().setAttribute(HOSPITAL_ADMIN_SESSION_GUBUN, gubun);
//    }
//
//    public void setEditorImgIdxs(String idxs) {
//        getSession().setAttribute(EDITOR_IMG_IDXS, idxs);
//    }
//
//    public void setHospitalAdminSessionSidoCode(String sidoCode) {getSession().setAttribute(HOSPITAL_ADMIN_SESSION_SIDOCODE, sidoCode);}
//
//    public void setHospitalAdminSessionSidoPage(String sidoPage) {getSession().setAttribute(HOSPITAL_ADMIN_SESSION_SIDOPAGE, sidoPage);}
//
//    public void setHospitalAdminSessionSidoName(String sidoName) {getSession().setAttribute(HOSPITAL_ADMIN_SESSION_SIDONAME, sidoName);}
//
//    public String getHospitalAdminSessionId() {
//        return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_ID);
//    }
//
//    public Long getHospitalAdminSessionIdx() {
//        return (Long) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_IDX);
//    }
//
//    public String getHospitalAdminSessionName() {
//        return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_NAME);
//    }
//
//    public Long getHospitalAdminSessionAuth() {
//        //return (Long) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_AUTH);
//        return Long.valueOf(String.valueOf(getSession().getAttribute(HOSPITAL_ADMIN_SESSION_AUTH)));
//    }
//
//    public String getHospitalAdminSessionGubun() {
//        return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_GUBUN);
//    }
//
//    public String getEditorImgIdxs() {
//        return (String) getSession().getAttribute(EDITOR_IMG_IDXS);
//    }
//
//    public String getHospitalAdminSessionSidoCode() {return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_SIDOCODE);}
//
//    public String getHospitalAdminSessionSidoPage() {return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_SIDOPAGE);}
//
//    public String getHospitalAdminSessionSidoName() {return (String) getSession().getAttribute(HOSPITAL_ADMIN_SESSION_SIDONAME);}
//    // LINE :: SESSION 데이터 처리 ====================================================================================================================================================

    /**
     * FUNCTION :: Ajax요청에 대한 리턴 맵 객체 선언
     *
     * @return
     */
    public HashMap<String, Object> returnMap(){
        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_FAIL);      /* 실패 */
        return rtnMap;
    }

    /**
     * FUNCTION :: jSon 형식 변환
     *
     * @param map
     * @return String
     */
    public String jsonFormatTransfer(Map<String, Object> map) {
        String rtnJson = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            rtnJson = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.info("jsonFormatTransfer["+e.getMessage()+"]");
        }

        return rtnJson;
    }

    /***
     * FUNCTION :: 공통 페이징 처리
     * @return
     */
//    public void setDefaultPaging(Model model, Paging pagingExtendedObject, int totalCount) {
//        pagingExtendedObject.setTotalCount(totalCount);  //전체 페이지수 세팅
//        model.addAttribute("totalCount", totalCount);
//        model.addAttribute("paging", pagingExtendedObject);
//    }
//
//    /**
//     * FUNCTION :: 기본 EXCEPTION 처리
//     *      트랙잭션 결과를 항상 롤백하도록 처리
//     *      입력된 결과 값을 결과 객체에 세팅
//     * @param rtnMap
//     * @param result
//     */
//    public void defaultExceptionHandling(Map<String, Object> rtnMap, String result) {
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        rtnMap.put(AJAX_RESULT_TEXT, result);
//    }
//
//
//    /**
//     * FUNCTION :: String builder 를 사용시 replace를 index로 처리해야 되기 때문에 해당 처리 로직 작성
//     * - 첫 번째 문자열 변환
//     * @param bodyStr
//     * @param target
//     * @param result
//     */
//    public void replaceBuilderFirst(StringBuilder bodyStr, String target, String result) {
//        int index = bodyStr.indexOf(target);
//        bodyStr.replace(index, index + target.length(), result);
//    }
//
//    /**
//     * FUNCTION :: String builder 를 사용시 replace를 index로 처리해야 되기 때문에 해당 처리 로직 작성
//     * - 마지막 문자열 변환
//     * @param bodyStr
//     * @param target
//     * @param result
//     */
//    public void replaceBuilderLast(StringBuilder bodyStr, String target, String result) {
//        int index = bodyStr.lastIndexOf(target);
//        if(index > -1) {
//            bodyStr.replace(index, index + target.length(), result);
//        }
//    }

}
