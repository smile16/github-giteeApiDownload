package com.gsmoled.myapplication.NetWork;

import java.util.List;

/**
 * 创建作者       : albus
 * 创建时间       : 2020/4/8
 * Fuction(类描述):
 */
public class GiteeRequestBean {

    /**
     * sha : 5a0c94d35c295e5896523d78b12d782c5c20549f
     * url : https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/trees/5a0c94d35c295e5896523d78b12d782c5c20549f
     * tree : [{"path":"dfu_ota_app_v1.0.26.zip","mode":"100644","type":"blob","sha":"f633a2019b677d229c0ae104f7f510a318f48ce8","size":74879,"url":"https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/blobs/f633a2019b677d229c0ae104f7f510a318f48ce8"},{"path":"dfu_ota_app_v1.0.27.zip","mode":"100644","type":"blob","sha":"1a45cd47b4074976b5df5b232f459bf0791a8ca8","size":74979,"url":"https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/blobs/1a45cd47b4074976b5df5b232f459bf0791a8ca8"}]
     * truncated : false
     */

    private String sha;
    private String url;
    private boolean truncated;
    private List<TreeBean> tree;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public List<TreeBean> getTree() {
        return tree;
    }

    public void setTree(List<TreeBean> tree) {
        this.tree = tree;
    }

    public static class TreeBean {
        /**
         * path : dfu_ota_app_v1.0.26.zip
         * mode : 100644
         * type : blob
         * sha : f633a2019b677d229c0ae104f7f510a318f48ce8
         * size : 74879
         * url : https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/blobs/f633a2019b677d229c0ae104f7f510a318f48ce8
         */

        private String path;
        private String mode;
        private String type;
        private String sha;
        private int size;
        private String url;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
