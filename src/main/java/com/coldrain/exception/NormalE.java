package com.coldrain.exception;

public class NormalE {

    @SuppressWarnings("serial")
    public static class NotFolderException extends Exception {

        private String retCd; // 异常对应的返回码
        private String msgDes; // 异常对应的描述信息

        public NotFolderException() {
            super();
        }

        public NotFolderException(String message) {
            super(message);
            msgDes = message;
        }

        public NotFolderException(String retCd, String msgDes) {
            super();
            this.retCd = retCd;
            this.msgDes = msgDes;
        }

        public String getRetCd() {
            return retCd;
        }

        public String getMsgDes() {
            return msgDes;
        }
    }



    @SuppressWarnings("serial")
    public static class NoCorrespondingFileTypeFound extends Exception {

        private String retCd; // 异常对应的返回码
        private String msgDes; // 异常对应的描述信息

        public NoCorrespondingFileTypeFound() {
            super();
        }

        public NoCorrespondingFileTypeFound(String message) {
            super(message);
            msgDes = message;
        }

        public NoCorrespondingFileTypeFound(String retCd, String msgDes) {
            super();
            this.retCd = retCd;
            this.msgDes = msgDes;
        }

        public String getRetCd() {
            return retCd;
        }

        public String getMsgDes() {
            return msgDes;
        }


    }
}
