package demo.ht.com.volley.bean;

import java.util.List;

public class GuoChuangYunBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","users":[]}
     * msg : V3.1.0 YesApi App.User.Search
     */

    private int ret;
    private DataBean data;
    private String msg;

    @Override
    public String toString() {
        return "GuoChuangYunBean{" +
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
         * users : []
         */

        private int err_code;
        private String err_msg;
        private List<?> users;

        @Override
        public String toString() {
            return "DataBean{" +
                    "err_code=" + err_code +
                    ", err_msg='" + err_msg + '\'' +
                    ", users=" + users +
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

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }
    }
}
