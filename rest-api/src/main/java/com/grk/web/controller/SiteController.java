package com.grk.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grk.core.event.AllPlayersEvent;
import com.grk.core.event.PlayerDetails;
import com.grk.core.event.RequestAllPlayersEvent;
import com.grk.core.services.PlayerService;
import com.grk.web.domain.PlayerListModel;
import com.grk.web.domain.PlayerSummaryWeb;

@Controller
@RequestMapping("/")
public class SiteController {

	@Autowired
	PlayerService playerService;

	@Autowired
	private PlayerListModel glennListModel;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SiteController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getAllPlayers(Model model) {
		LOG.debug("Yummy menu response directly to ResponseBody, model: {}", model.getClass());
		LOG.debug(""+ model.toString());
		model.addAttribute("playerList", getPlayerList(new RequestAllPlayersEvent()));
		LOG.debug(""+ model.toString());
		return "home";
		
//		return prettyPrint(playerService
//				.getAllPlayers(new RequestAllPlayersEvent()));
	}

	private List<PlayerSummaryWeb> getPlayerList(RequestAllPlayersEvent evt) {
		List<PlayerSummaryWeb> playerWebDetails = new ArrayList<PlayerSummaryWeb>();
		
		AllPlayersEvent result = playerService.getAllPlayers(new RequestAllPlayersEvent());
		LOG.debug("AllPlayersEvent.result # of players: {}", result.getPlayerDetails().size());
		for (PlayerDetails playerDetails : result.getPlayerDetails()) {
			playerWebDetails.add(PlayerSummaryWeb.fromPlayerDetails(playerDetails));
		}
		LOG.debug("playerWebDetails # of players: {}", playerWebDetails.size());
		return playerWebDetails;
		
	}
	
	@ModelAttribute("bobkey")
	private PlayerListModel getPlayerListModel() {
		LOG.debug("*** getPlayerListModel() {}", glennListModel.getClass());
		return glennListModel;
	}
	
//	private String prettyPrint(AllPlayersEvent requestAllPlayers) {
//		StringBuffer sb = new StringBuffer();
//		String delim = "";
//		for (PlayerDetails playerDetails : requestAllPlayers.getPlayerDetails()) {
//			sb.append(delim).append(playerDetails.getId());
//			delim = ",";
//			sb.append(delim).append(playerDetails.getLastname());
//		}
//		return sb.toString();
//	}

}
