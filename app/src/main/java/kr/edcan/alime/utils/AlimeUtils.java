package kr.edcan.alime.utils;

/**
 * Created by JunseokOh on 2016. 8. 18..
 */
public class AlimeUtils {
    public static String[] type = ("금형/CNC선반/용접/자동차차체수리/판금/배관/" +
            "동력제어/전기기기/산업용로봇/가구/건축설계CAD/그래픽디자인/제품디자인/폴리메카닉스" +
            "/농업기계정비/냉동기술/프로토타입모델링/공업전자기기/컴퓨터정보통신/통신망분배기술/애니메이션" +
            "/기계설계CAD/자동차정비/실내장식/목공/목공예/CNC밀링/주조/옥내제어/정보기술/귀금속공예/보석가공" +
            "/웹디자인및개발/게임개발/조적/미장/타일/메카트로닉스/자동차페인팅/모바일로보틱스/석공예/도자기" +
            "/화훼장식/헤어디자인/의상디자인/한복/요리/제과제빵/피부미용").split("/");

    public AlimeUtils() {
    }

    public static String[] getType() {
        return type;
    }

    public static String getTypeByIndex(int position) {
        if (position > type.length) return "";
        return type[position];
    }
}
