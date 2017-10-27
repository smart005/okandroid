图片(文件)上传
--------
	FileUploadUtils fileUploadUtils = new FileUploadUtils() {
        @Override
        protected Activity getActivity() {
            return PersonalInfoActivity.this;
        }

        @Override
        protected String getEndpoint() {
            return APIConfigProcess.getBasicConfigBean().getEndpoint();
        }

        @Override
        protected String getBucket() {
            return APIConfigProcess.getBasicConfigBean().getBucket();
        }

        @Override
        protected void onUploadSuccess(int position, String url, String updateType, Object extra) {
            //上传成功回调
        }

        @Override
        protected void onCompleted() {
        	//上传完成回调
        }
    };

    //上传目录配置
    fileUploadUtils.setUploadDirectoryFormat(String.format(OssUploadDirectory.userimg.getValue(),
            DateUtils.getDateTime(DateFormatEnum.YYYYMMNC)));
    fileUploadUtils.upload("上传文件名（示例:xxxxxx.png）", 上传文件, "上传时显示文字", token);

    