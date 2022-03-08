import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
        List<String> videoIdList = new ArrayList<>();
        videoIdList.add("1");
        videoIdList.add("2");
        videoIdList.add("3");

        StringBuilder builder = new StringBuilder();
        videoIdList.forEach(item -> {
            builder.append(item).append(",");
        });
        System.out.println(builder.replace(builder.length() - 1, builder.length(), "").toString());
    }

    /**
     * 上传视频
     */
    public static void uploadVideo() {
        UploadVideoRequest request = new UploadVideoRequest(
                "LTAI5t91gH2au32Jk8ZennUg",
                "aF9Uvoea07f6sf8ozLYjnLlF54aMUP",
                "测试视频",
                "D:\\project\\guli_parent\\service\\service_vod\\src\\test\\resources\\6 - What If I Want to Move Faster.mp4"
        );
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.println(response.getVideoId());
        } else {
            System.out.println(response.getVideoId());
            System.out.println(response.getCode());
            System.out.println(response.getMessage());
        }
    }

    /**
     * 获取视频播放品证
     */
    public static void getPlayAuth() throws ClientException {
        // 根据视频id获取视频播放凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t91gH2au32Jk8ZennUg", "aF9Uvoea07f6sf8ozLYjnLlF54aMUP");

        // 构建请求对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("3abf2af84da4406a8148cca624a62f4f");

        // 获取响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }

    /**
     * 获取播放链接
     */
    public static void getPlayUrl() throws ClientException {
        // 构建请求客户端
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t91gH2au32Jk8ZennUg", "aF9Uvoea07f6sf8ozLYjnLlF54aMUP");

        // 构建请求对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("3abf2af84da4406a8148cca624a62f4f");

        // 获取响应
        GetPlayInfoResponse response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> list = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : list) {
            System.out.println(playInfo.getPlayURL());
        }
    }
}
