package com.WebPortfolio.Controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.WebPortfolio.Service.InstagramService;
import com.WebPortfolio.Service.MessageService;
import com.WebPortfolio.Service.PortfolioService;
import com.WebPortfolio.Service.VisitorService;

@RunWith(SpringJUnit4ClassRunner.class)
public class MainColtollerTests {

	@Mock
	InstagramService instagramService;

	@Mock
	PortfolioService portfolioService;

	@Mock
	MessageService messageService;

	@Mock
	VisitorService visitorService;

	@Value("${upload.portfolioPath}")
	String portfolioPath;

	@InjectMocks
	private MainController maincontroller;

	private MockMvc mockMvc;

	private Map<String, Object> params;
	private ModelAndView mv;

	public MockHttpSession session;
	public MockHttpServletRequest request;
	public Model model;


	@Before
	public void setUp() throws Exception {

		params = new HashMap<>();
		mv = new ModelAndView();  
		session = new MockHttpSession();
		request = new MockHttpServletRequest();

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(maincontroller).build();
	}
	
	@Test
    public void testIndexView() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();

        
        mockMvc.perform(get("/").session(session)).andExpect(status().isOk())
                                                                  .andExpect(view().name("index"));
        
        mv = maincontroller.home(mv, request);        
	       
        assertTrue(mv.getModel().containsKey("datalist")); // return 됐을 때 해당 키를 포함하고 있는지 확인
        assertTrue(mv.getModel().containsKey("portfolioPath"));
        assertTrue(mv.getModel().containsKey("instagramList"));
        assertTrue(mv.getModel().containsKey("portfolioList"));
        
        verify(portfolioService, times(2)).getPortfolioList();
                
    }		
}
