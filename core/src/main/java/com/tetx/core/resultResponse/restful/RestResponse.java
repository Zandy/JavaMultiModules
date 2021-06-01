package com.tetx.core.resultResponse.restful;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tetx.core.resultResponse.Result;
import com.tetx.core.resultResponse.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * @Description Restful 接口返回结果
 */
public class RestResponse<T> extends Result {
    protected int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp = LocalDateTime.now();

    public RestResponse(ResultCode resultCode, T data, T errors, Throwable throwable) {
        super(resultCode, data, errors, throwable);
        this.status = Integer.valueOf(String.valueOf(resultCode.getCode()).substring(0, 3));
    }

    public RestResponse(int code, String msg, T data, T errors, Throwable throwable) {
        super(code, msg, data, errors, throwable);
        this.status = Integer.valueOf(String.valueOf(code).substring(0, 3));
    }

    public RestResponse(int status, ResultCode resultCode, T data, T errors, Throwable throwable) {
        super(resultCode, data, errors, throwable);
        this.status = status;
    }

    public RestResponse(int status, int code, String msg, T data, T errors, Throwable throwable) {
        super(code, msg, data, errors, throwable);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RestResponse) {
            RestResponse re = (RestResponse) o;
            return getStatus() == re.getStatus() &&
                    getCode() == re.getCode() &&
                    ObjectUtils.nullSafeEquals(getMsg(), re.getMsg()) &&
                    ObjectUtils.nullSafeEquals(getData(), re.getData()) &&
                    ObjectUtils.nullSafeEquals(getErrors(), re.getErrors()) &&
                    ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable()) &&
                    ObjectUtils.nullSafeEquals(getTimestamp(), re.getTimestamp());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(new Object[]{
                getStatus(), getCode(), getMsg(), getData(), getErrors(), getThrowable(), getTimestamp()
        });
    }

    public static class Builder<T> extends Result.Builder {

        protected HttpStatus status;

        public Builder setStatus(int statusCode) {
            this.status = HttpStatus.valueOf(statusCode);
            return this;
        }

        public Builder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        @Override
        public Builder setCode(ResultCode resultCode) {
            super.setCode(resultCode);
            this.status = HttpStatus.valueOf(String.valueOf(this.code).substring(3));
            return this;
        }

        @Override
        public RestResponse<T> build() {
            if (this.status == null) {
                //this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                return new RestResponse(this.code, this.msg, this.data, this.errors, this.throwable);
            }
            return new RestResponse(this.status.value(), this.code, this.msg, this.data, this.errors, this.throwable);
        }
    }
}
