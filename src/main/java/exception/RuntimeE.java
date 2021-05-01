package exception;

public class RuntimeE {
    
    @SuppressWarnings("serial")
    public static class EmptyListException extends RuntimeException {
        
        private String retCd ;  //异常对应的返回码
        private String msgDes;  //异常对应的描述信息
        
        public EmptyListException() {
            super();
        }
     
        public EmptyListException(String message) {
            super(message);
            msgDes = message;
        }
     
        public EmptyListException(String retCd, String msgDes) {
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
