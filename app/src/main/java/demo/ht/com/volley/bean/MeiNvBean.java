package demo.ht.com.volley.bean;

import java.util.List;

public class MeiNvBean {

    /**
     * error : false
     * results : [{"_id":"5be14edb9d21223dd50660f8","createdAt":"2018-11-06T08:20:43.656Z","desc":"2018-11-06","publishedAt":"2018-11-06T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/8733844e0b954e7e8e29102cefa32dbf","used":true,"who":"lijinshanmx"},{"_id":"5bcd71979d21220315c663fc","createdAt":"2018-10-22T06:43:35.440Z","desc":"2018-10-22","publishedAt":"2018-10-22T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/0bc3ed0deda74674b45bebb140bb5928","used":true,"who":"lijinshanmx"},{"_id":"5bc434ac9d212279160c4c9e","createdAt":"2018-10-15T06:33:16.497Z","desc":"2018-10-15","publishedAt":"2018-10-15T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/1635f0ffd94f40ff8058c2c4ff652e61","used":true,"who":"lijinshanmx"},{"_id":"5bbb0de09d21226111b86f1c","createdAt":"2018-10-08T07:57:20.978Z","desc":"2018-10-08","publishedAt":"2018-10-08T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/e930b75f48ae40b0a23f827e619d76a3","used":true,"who":"lijinshanmx"},{"_id":"5ba206ec9d2122610aba3440","createdAt":"2018-09-19T08:21:00.295Z","desc":"2018-09-19","publishedAt":"2018-09-19T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/d19c8feacc884e18898034c79e6e1a9d","used":true,"who":"lijinshanmx"},{"_id":"5b9771a29d212206c1b383d0","createdAt":"2018-09-11T07:41:22.491Z","desc":"2018-09-11","publishedAt":"2018-09-11T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/38ddf2ca672345889343b5748ffce78d","used":true,"who":"lijinshanmx"},{"_id":"5b830bba9d2122031f86ee51","createdAt":"2018-08-27T04:21:14.703Z","desc":"2018-08-27","publishedAt":"2018-08-28T00:00:00.0Z","source":"web","type":"福利","url":"https://img.lijinshan.site/images/a8aa7df22298413e9ca37c7a634e3425","used":true,"who":"lijinshanmx"},{"_id":"5b7b836c9d212201e982de6e","createdAt":"2018-08-21T11:13:48.989Z","desc":"2018-08-21","publishedAt":"2018-08-21T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    @Override
    public String toString() {
        return "MeiNvBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5be14edb9d21223dd50660f8
         * createdAt : 2018-11-06T08:20:43.656Z
         * desc : 2018-11-06
         * publishedAt : 2018-11-06T00:00:00.0Z
         * source : web
         * type : 福利
         * url : https://img.lijinshan.site/images/8733844e0b954e7e8e29102cefa32dbf
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
