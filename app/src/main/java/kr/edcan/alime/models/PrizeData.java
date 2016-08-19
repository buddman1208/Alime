package kr.edcan.alime.models;

/**
 * Created by JunseokOh on 2016. 8. 19..
 */
public class PrizeData {
    private String prizeName, medalName, prizeMoneyCount, content;

    public PrizeData(String prizeName, String medalName, String prizeMoneyCount, String content) {
        this.prizeName = prizeName;
        this.medalName = medalName;
        this.prizeMoneyCount = prizeMoneyCount;
        this.content = content;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public String getMedalName() {
        return medalName;
    }

    public String getPrizeMoneyCount() {
        return prizeMoneyCount;
    }

    public String getContent() {
        return content;
    }
}
