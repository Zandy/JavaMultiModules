package com.tetx.core.resultResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description 统一返回结果
 */
public class Result<T> implements Serializable {
    protected int code;
    protected String msg;
    protected T data;
    protected T errors;
    protected Throwable throwable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp = LocalDateTime.now();

    public Result(ResultCode resultCode, T data, T errors, Throwable throwable) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
        this.errors = errors;
        this.throwable = throwable;
    }

    public Result(int code, String msg, T data, T errors, Throwable throwable) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.errors = errors;
        this.throwable = throwable;
    }

    public Result() {

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public T getData() {
        return data;
    }

    public T getErrors() {
        return errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Result) {
            Result re = (Result) o;
            return getCode() == re.getCode() &&
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
                getCode(), getMsg(), getData(), getErrors(), getThrowable(), getTimestamp()
        });
    }

    public static class Builder<T> {

        protected int code;
        protected String msg;
        protected T data;
        protected T errors;
        protected Throwable throwable;
        protected LocalDateTime timestamp = LocalDateTime.now();

        public Builder() {
        }

        public Builder setCode(ResultCode resultCode) {
            this.code = resultCode.getCode();
            this.msg = resultCode.getMsg();
            return this;
        }

        public Builder setData(T data) {
            this.data = data;
            return this;
        }

        public Builder setErrors(T errors) {
            this.errors = errors;
            return this;
        }

        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Result<T> build() {
            return new Result(this.code, this.msg, this.data, this.errors, this.throwable);
        }
    }
}
