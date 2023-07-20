package SignOnReminder;

import java.util.Objects;

public class LogsModel {
    private String bankCode;
    private String environment;
    private int port;
    private String bankName;
    private boolean status;



    public LogsModel(LogsModel log) {
        this.bankCode = log.bankCode;
        this.environment = log.environment;
        this.port = log.port;
        this.bankName = log.bankName;
        this.status = log.status;
    }
    public LogsModel(String bankCode, String environment, int port, String bankName, boolean status) {
        this.bankCode = bankCode;
        this.environment = environment;
        this.port = port;
        this.bankName = bankName;
        this.status = status;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LogsModel)) {
            return false;
        }
        LogsModel log = (LogsModel) o;
        return Objects.equals(bankCode, log.bankCode) && Objects.equals(environment, log.environment)  && port == log.port && Objects.equals(bankName, log.bankName) && status == log.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankCode,environment,port,bankName,status);
    }

    @Override
    public String toString() {
        String statusString = (status == false) ? "Offline" : "Online";
        return "- Envi " + environment + " Port " + port + " terpantau " + statusString + "\n";
    }

}
