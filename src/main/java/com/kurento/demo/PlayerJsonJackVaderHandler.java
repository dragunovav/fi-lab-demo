/*
 * (C) Copyright 2013 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.kurento.demo;

import com.kurento.kmf.content.HttpPlayerHandler;
import com.kurento.kmf.content.HttpPlayerService;
import com.kurento.kmf.content.HttpPlayerSession;
import com.kurento.kmf.media.JackVaderFilter;
import com.kurento.kmf.media.MediaPipeline;
import com.kurento.kmf.media.MediaPipelineFactory;
import com.kurento.kmf.media.PlayerEndPoint;

/**
 * HTTP Player Handler which plays a media pipeline composed by a
 * <code>PlayerEndPoint</code> with a <code>JackVaderFilter</code>; using
 * redirect strategy; with JSON signaling protocol.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @author Boni García (bgarcia@gsyc.es)
 * @version 1.0.0
 */
@HttpPlayerService(name = "PlayerJonJackVaderHandler", path = "/playerJsonJackVader", useControlProtocol = true)
public class PlayerJsonJackVaderHandler extends HttpPlayerHandler {

	@Override
	public void onContentRequest(HttpPlayerSession session) throws Exception {
		MediaPipelineFactory mpf = session.getMediaPipelineFactory();
		MediaPipeline mp = mpf.create();
		session.releaseOnTerminate(mp);
		PlayerEndPoint playerEndPoint = mp.createPlayerEndPoint(VideoURLs.map
				.get("jack"));
		JackVaderFilter filter = mp.createJackVaderFilter();
		playerEndPoint.connect(filter);
		session.setAttribute("player", playerEndPoint);
		session.start(filter);
	}

	@Override
	public void onContentStarted(HttpPlayerSession session) {
		PlayerEndPoint playerendPoint = (PlayerEndPoint) session
				.getAttribute("player");
		playerendPoint.play();
	}

}