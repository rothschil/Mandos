package xyz.wongs.drunkard.common.entertainment;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import xyz.wongs.drunkard.base.constant.Constant;
import xyz.wongs.drunkard.base.utils.MathUtil2;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.base.utils.file.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName EnterTainmentUtil
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/13 14:44
 * @Version 1.0.0
 */
public class EnterTainmentUtil {

    private static final String FFPROBE ="ffprobe";

    private static final Logger LOG = LoggerFactory.getLogger(EnterTainmentUtil.class);

    private static ImmutableList<Object> videoSuffixList = ImmutableList.copyOf(Arrays.stream(EntertrainmentEnum.values()).toArray());

    /**
     * @Description
     * @param file
     * @return int
     * @throws
     * @date 21/4/13 15:22
     */
    public static int getVideoDuration(File file){
        int duration = 0;
        File tempFile = null;
        try {
            tempFile = File.createTempFile(StringUtils.getUuid(), Constant.POINT+  FileUtil.getPrefix(file));
            FileInputStream fss = new FileInputStream(file);
            FileUtils.copyToFile(fss,tempFile);
            MultimediaObject multimediaObject = new MultimediaObject(tempFile);
            MultimediaInfo info = multimediaObject.getInfo();
            duration = MathUtil2.getInteger((double) info.getDuration(),1000);
        } catch (IOException e) {
            LOG.error("临时文件生成错误");
        } catch (InputFormatException e) {
            LOG.error("时间在格式化中发生 错误");
        } catch (EncoderException e) {
            LOG.error("文件序列化错误");
        } finally {
            if (null!=tempFile){
                tempFile.deleteOnExit();
            }
        }
        return duration;
    }

    public static boolean isVideo(MultipartFile file){
        return videoSuffixList.contains(FilenameUtils.getExtension(file.getOriginalFilename()));
    }


    public static void ffmpeg(String ffmengPath,String inputFile){
        try {
            FFprobe ffprobe = new FFprobe(ffmengPath+Constant.SLASH+FFPROBE);
            FFmpegProbeResult probeResult = ffprobe.probe(inputFile);
            FFmpegFormat format = probeResult.getFormat();


            System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
                    format.filename,
                    format.format_long_name,
                    format.duration
            );

            FFmpegStream stream = probeResult.getStreams().get(0);

            System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
                    stream.codec_long_name,
                    stream.width,
                    stream.height
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        int in = EnterTainmentUtil.getVideoDuration(new File("F:\\Repertory\\Tecent\\TIM\\297130401\\FileRecv\\IIO.MOV"));
//        System.out.println(in);
        String ffmengPath = System.getenv().get("FFMPEG_HOME");
        ffmpeg(ffmengPath,"F:\\Repertory\\Tecent\\TIM\\297130401\\FileRecv\\IIO.MOV");

    }

}
