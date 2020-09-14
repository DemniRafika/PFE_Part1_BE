package net.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.repository.TradeRepository;
import net.service.SequenceGeneratorService;
import net.exception.ResourceNotFoundException;

import net.model.Trade;
import net.model.TradeMessageSender;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")

@Path("Trade")
public class TradeController {
	
	@Autowired
	private TradeMessageSender tradeMessageSender;
	
	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/trades")
	@Produces({MediaType.TEXT_PLAIN })
	public List<Trade> getAllTrades(){
		
		return tradeRepository.findAll();
	}
	
	// in case we have a database
	@GetMapping("/trades/{id}")
	@Produces({MediaType.TEXT_PLAIN })
	public ResponseEntity<Trade> getTradeById(@PathVariable(value = "id") Long tradeId)
			throws ResourceNotFoundException {
		Trade trade = tradeRepository.findById(tradeId)
				.orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + tradeId));
		return ResponseEntity.ok().body(trade);
	}
	
	// this is the Rest api that we need in our project
	
	@PostMapping("/trades")
	@Consumes({MediaType.TEXT_PLAIN })
	public Trade createTrade(@Valid @RequestBody Trade trade , RedirectAttributes redirectAttributes) {
		
	    tradeMessageSender.sendOrder(trade);
        redirectAttributes.addFlashAttribute("message", "Trade message sent successfully");
        //inject trade into database
		trade.setId(sequenceGeneratorService.generateSequence(Trade.SEQUENCE_NAME));
		//System.out.println("BS : "+trade.getBs());
		return tradeRepository.save(trade);
		
	}
	//CRUD in case we have a database
	@PutMapping("/trades/{id}")
	@Consumes({MediaType.TEXT_PLAIN })
	public ResponseEntity<Trade> updateTrade(@PathVariable(value = "id") Long tradeId,
			@Valid @RequestBody Trade tradeDetails) throws ResourceNotFoundException {
		Trade trade = tradeRepository.findById(tradeId)
				.orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + tradeId));

		trade.setFirm(tradeDetails.getFirm());
		trade.setDate(tradeDetails.getDate());
		trade.setProduct(tradeDetails.getProduct());
		trade.setContract(tradeDetails.getContract());
		trade.setBs(tradeDetails.getBs());
		trade.setQty(tradeDetails.getQty());
		trade.setPrice(tradeDetails.getPrice());
		trade.setAccount(tradeDetails.getAccount());
		trade.setOrder(tradeDetails.getOrder());
		trade.setOrg(tradeDetails.getOrg());
		trade.setCti(tradeDetails.getCti());
		trade.setTradedAs(tradeDetails.getTradedAs());
		trade.setOrderType(tradeDetails.getOrderType());
		trade.setBkr(tradeDetails.getBkr());
		trade.setOppFirm(tradeDetails.getOppFirm());
		trade.setOppBrkr(tradeDetails.getOppBrkr());
		trade.setTimeBrkt(tradeDetails.getTimeBrkt());
		
		final Trade updatedTrade = tradeRepository.save(trade);
		return ResponseEntity.ok(updatedTrade);
	}
	
	//CRUD in case we have a database
	@DeleteMapping("/trades/{id}")
	public Map<String, Boolean> deleteTrade(@PathVariable(value = "id") Long tradeId)
			throws ResourceNotFoundException {
		Trade trade = tradeRepository.findById(tradeId)
				.orElseThrow(() -> new ResourceNotFoundException("Trade not found for this id :: " + tradeId));

		tradeRepository.delete(trade);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
