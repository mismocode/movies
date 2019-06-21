package es.mismocode.movies.services;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

import es.mismocode.movies.model.MovieMetaData;

public class MetaDataService {
	
	private static final Map<ICodec.ID, String> AUDIO_CODEC_CONVERTER = new HashMap<ICodec.ID, String>();
	private static final Map<ICodec.ID, String> VIDEO_CODEC_CONVERTER = new HashMap<ICodec.ID, String>();
	private static final Map<Integer, String> CHANNELS_CONVERTER = new HashMap<Integer, String>();
	private static final Map<Integer, String> SAMPLE_RATE_CODEC_CONVERTER = new HashMap<Integer, String>();
	
	public MetaDataService() {
		AUDIO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_AC3, "AC3");
		AUDIO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_DTS, "DTS");
		AUDIO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_MP2, "MP2");
		AUDIO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_MP3, "MP3");
		
		VIDEO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_H264, "H.264");
		VIDEO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_MPEG2VIDEO, "MPEG-2");
		VIDEO_CODEC_CONVERTER.put(ICodec.ID.CODEC_ID_MPEG4, "MPEG-4");
		
		CHANNELS_CONVERTER.put(0, "-");
		CHANNELS_CONVERTER.put(2, "Stereo");
		CHANNELS_CONVERTER.put(6, "5.1");
		
		SAMPLE_RATE_CODEC_CONVERTER.put(0, "-");
		SAMPLE_RATE_CODEC_CONVERTER.put(32000, "32 kHz");
		SAMPLE_RATE_CODEC_CONVERTER.put(44100, "44.1 kHz");
		SAMPLE_RATE_CODEC_CONVERTER.put(48000, "48 kHz");
	}
	
	public MovieMetaData getMetaData(final String filename) {
		boolean isAudioFilled = false;
		boolean isVideoFilled = false;
		
		if(StringUtils.isNotBlank(filename)) {
			try {
				IContainer container = IContainer.make();
				int result = container.open(filename, IContainer.Type.READ, null);
				if (result >= 0) {
					int numStreams = container.getNumStreams();

					MovieMetaData movieMetaData = new MovieMetaData();
					movieMetaData.setSize(new Double(new File(filename).length()/(1024 * 1024)).intValue());
			    	for (int i = 0; i < numStreams; i++) {
			    		IStream stream = container.getStream(i);
			    		IStreamCoder coder = stream.getStreamCoder();

			    		if (!isAudioFilled && coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
			    			movieMetaData.setAudioCodec(AUDIO_CODEC_CONVERTER.get(coder.getCodecID()));
			    			movieMetaData.setAudioChannels(CHANNELS_CONVERTER.get(coder.getChannels()));
			    			movieMetaData.setAudioSampleRate(SAMPLE_RATE_CODEC_CONVERTER.get(coder.getSampleRate()));
			    			movieMetaData.setAudioSampleRateOriginal(coder.getSampleRate());
			    			isAudioFilled = true;
			    		} else if (!isVideoFilled && coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
			    			movieMetaData.setVideoCodec(VIDEO_CODEC_CONVERTER.get(coder.getCodecID()));
			    			movieMetaData.setVideoWidth(coder.getWidth());
			    			movieMetaData.setVideoHeight(coder.getHeight());
			    			isVideoFilled = true;
			    		}
			    	}					
					return movieMetaData;
				}
			} catch(final Exception e) {
				// TODO
				e.printStackTrace();
			}
		}
		return null;
	}
}
