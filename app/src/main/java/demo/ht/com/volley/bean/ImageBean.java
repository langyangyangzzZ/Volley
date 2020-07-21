package demo.ht.com.volley.bean;

public class ImageBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","url":"http://cdn7.okayapi.com/20170920223113_62b11e9002a749d4d468824b0c10bbd6"}
     * msg :
     */

    private int ret;
    private DataBean data;
    private String msg;

    @Override
    public String toString() {
        return "ImageBean{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * err_code : 0
         * err_msg :
         * url : http://cdn7.okayapi.com/20170920223113_62b11e9002a749d4d468824b0c10bbd6
         */

        private int err_code;
        private String err_msg;
        private String url;

        @Override
        public String toString() {
            return "DataBean{" +
                    "err_code=" + err_code +
                    ", err_msg='" + err_msg + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public int getErr_code() {
            return err_code;
        }

        public void setErr_code(int err_code) {
            this.err_code = err_code;
        }

        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
