package com.softserve.osbb.test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.City;
import com.softserve.osbb.model.District;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.Street;
import com.softserve.osbb.repository.CityRepository;
import com.softserve.osbb.repository.DistrictRepository;
import com.softserve.osbb.repository.OsbbRepository;
import com.softserve.osbb.repository.RegionRepository;
import com.softserve.osbb.repository.StreetRepository;
import com.softserve.osbb.service.AddressService;
import com.softserve.osbb.service.impl.AddressServiceImpl;
import com.softserve.osbb.service.impl.OsbbServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class AddressServiceTest {

	@InjectMocks
	private AddressServiceImpl addressServiceImpl;
	
	@Mock
	private OsbbServiceImpl osbbServiceImpl;
	
	@Mock
	private AddressService addressService;
	
	@Mock
	private RegionRepository regionRepository;
	
	@Mock
	private CityRepository cityRepository;
	
	@Mock
	private StreetRepository streetRepository;
	
	@Mock
	private DistrictRepository districtRepository;
	
	@Mock
	private OsbbRepository osbbRepository;
	
	@Mock
	private Region region;
	
	@Mock
	private City city;
	
	@Mock
	private Street street;
	
	@Mock
	private District district;
	
	@Mock
	private House house;
	
	@Mock
	private Osbb osbb;
	
	@Mock
	private List<Region> listOfRegion;
	
	@Mock
	private List<City> listOfCity;
	
	@Mock
	private Collection<City> cities;
	
	@Mock
	private List<Street> listOfStreet;
	
	@Mock
	private List<District> listOfDistrict;
	
	@Mock
	private List<Osbb> listOfOsbb;
	
	@Mock
	private Collection<Street> streets;
	
	@Mock
	private Collection<House> houses;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllRegionTest() {
		Region region = new Region();
		
		region.setId(1);
		region.setName("region1");
		
		when(addressServiceImpl.getAllRegion()).thenReturn(listOfRegion);
		
		addressServiceImpl.getAllRegion();
		
		assertNotNull(region);
		
		verify(regionRepository, times(1)).findAllByOrderByNameAsc();
	}
	
	@Test
	public void getAllCitiesOfRegionTest() {
		when(addressServiceImpl.getAllCitiesOfRegion(1)).thenReturn(listOfCity);
		when(addressServiceImpl.getAllCitiesOfRegion(2)).thenReturn(listOfCity);
		
		addressServiceImpl.getAllCitiesOfRegion(1);
		addressServiceImpl.getAllCitiesOfRegion(2);
		
		verify(cityRepository, times(2)).findByRegionOrderByName(regionRepository.findById(1));
		verify(cityRepository, times(2)).findByRegionOrderByName(regionRepository.findById(2));
	}
	
	@Test
	public void getAllStreetsOfCityTest() {
		when(addressServiceImpl.getAllStreetsOfCity(1)).thenReturn(listOfStreet);
		when(addressServiceImpl.getAllStreetsOfCity(2)).thenReturn(listOfStreet);
		
		addressServiceImpl.getAllStreetsOfCity(1);
		addressServiceImpl.getAllStreetsOfCity(2);
		
		verify(streetRepository, times(2)).findByCityOrderByName(cityRepository.findById(1));
		verify(streetRepository, times(2)).findByCityOrderByName(cityRepository.findById(2));
	}
	
	@Test
	public void getAllDistrictsOfCityTest() {
		when(addressServiceImpl.getAllDistrictsOfCity(1)).thenReturn(listOfDistrict);
		when(addressServiceImpl.getAllDistrictsOfCity(2)).thenReturn(listOfDistrict);
		
		addressServiceImpl.getAllDistrictsOfCity(1);
		addressServiceImpl.getAllDistrictsOfCity(2);
		
		verify(districtRepository, times(2)).findByCityOrderByName(cityRepository.findById(1));
		verify(districtRepository, times(2)).findByCityOrderByName(cityRepository.findById(2));
	}
	
	@Test
	public void getDistrictByIdTest() {
		when(addressServiceImpl.getDistrictById(1)).thenReturn(district);
		when(addressServiceImpl.getDistrictById(2)).thenReturn(district);
		
		addressServiceImpl.getDistrictById(1);
		addressServiceImpl.getDistrictById(2);
		
		verify(districtRepository, times(1)).findById(1);
		verify(districtRepository, times(1)).findById(2);
	}
	
	@Test
	public void getStreetByIdTest() {
		when(addressServiceImpl.getStreetById(1)).thenReturn(street);
		when(addressServiceImpl.getStreetById(2)).thenReturn(street);
		
		addressServiceImpl.getStreetById(1);
		addressServiceImpl.getStreetById(2);
		
		verify(streetRepository, times(1)).findById(1);
		verify(streetRepository, times(1)).findById(2);
	}
	
	@Test
	public void getCityByIdTest() {
		when(addressServiceImpl.getCityById(1)).thenReturn(city);
		when(addressServiceImpl.getCityById(2)).thenReturn(city);
		
		addressServiceImpl.getCityById(1);
		addressServiceImpl.getCityById(2);
		
		verify(cityRepository, times(1)).findById(1);
		verify(cityRepository, times(1)).findById(2);
	}
	
	@Test
	public void getRegionByIdTest() {
		when(addressServiceImpl.getRegionById(1)).thenReturn(region);
		when(addressServiceImpl.getRegionById(2)).thenReturn(region);
		
		addressServiceImpl.getRegionById(1);
		addressServiceImpl.getRegionById(2);
		
		verify(regionRepository, times(1)).findById(1);
		verify(regionRepository, times(1)).findById(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addRegionTest() {
		when(addressServiceImpl.addRegion(region)).thenReturn(region);
		
		addressServiceImpl.addRegion(region);
		addressServiceImpl.addRegion(null);
		
		verify(regionRepository, times(1)).saveAndFlush(region);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateRegionTest() {
		Region region = new Region();
		Region region2 = new Region();
		
		region.setId(1);
		region.setName("region1");
		region.setCities(null);
		
		region2.setId(1);
		
		addressServiceImpl.addRegion(region);
		
		when(regionRepository.exists(region.getId())).thenReturn(true);
		when(regionRepository.exists(region2.getId())).thenReturn(false);
		when(addressServiceImpl.updateRegion(region)).thenReturn(region);
		
		addressServiceImpl.updateRegion(region);
		addressServiceImpl.updateRegion(region2);
		when(addressServiceImpl.updateRegion(null));
		
		verify(regionRepository, times(1)).saveAndFlush(region);
	}
	
	@Test
	public void deleteRegionTest() {
		Region region = new Region();
		Region region2 = new Region();
		
		region.setId(1);
		region.setName("region1");
		region.setCities(null);
		
		region2.setId(1);
		
		addressServiceImpl.addRegion(region);		
				
		when(addressServiceImpl.getAllCitiesOfRegion(1)).thenReturn(listOfCity);
		when(addressServiceImpl.getAllCitiesOfRegion(1).isEmpty()).thenReturn(false);
		when(addressServiceImpl.getAllCitiesOfRegion(2).isEmpty()).thenReturn(true);
				
		addressServiceImpl.deleteRegion(1);		
		addressServiceImpl.deleteRegion(2);
		
		
		verify(regionRepository, times(1)).delete(1);
		verify(regionRepository, times(1)).delete(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addCityTest() {
		when(addressServiceImpl.addCity(city)).thenReturn(city);
		when(addressServiceImpl.addCity(null)).thenReturn(city);
		
		addressServiceImpl.addCity(city);
		addressServiceImpl.addCity(null);
		
		verify(cityRepository, times(1)).saveAndFlush(city);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateCityTest() {
		City city = new City();
		City city2 = new City();
		
		city.setId(1);
		city.setName("Lviv");
		city.setRegion(region);
		city.setStreets(streets);
		
		city2.setId(2);
		
		addressServiceImpl.addCity(city);
		
		when(cityRepository.exists(city.getId())).thenReturn(true);
		when(cityRepository.exists(city2.getId())).thenReturn(false);
		when(addressServiceImpl.updateCity(city)).thenReturn(city);
		
		addressServiceImpl.updateCity(city);
		addressServiceImpl.updateCity(city2);
		regionRepository.exists(city.getId());
		when(addressServiceImpl.updateCity(null));
		
		verify(cityRepository, times(1)).saveAndFlush(city);
	}
	
	@Test
	public void deleteCityTest() {
		City city = new City();
		
		city.setId(1);
		city.setName("Lviv");
		city.setRegion(region);
		city.setStreets(streets);
		
		addressServiceImpl.addCity(city);
		
		when(addressServiceImpl.getAllStreetsOfCity(1)).thenReturn(listOfStreet);
		when(addressServiceImpl.getAllStreetsOfCity(1).isEmpty()).thenReturn(false);
		when(addressServiceImpl.getAllStreetsOfCity(2).isEmpty()).thenReturn(true);
		
		addressServiceImpl.deleteCity(1);
		addressServiceImpl.deleteCity(2);
		
		verify(cityRepository, times(1)).delete(1);
		verify(cityRepository, times(1)).delete(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addStreetTest() {
		when(addressServiceImpl.addStreet(street)).thenReturn(street);
		when(addressServiceImpl.addStreet(null)).thenReturn(street);
		
		addressServiceImpl.addStreet(street);
		addressServiceImpl.addStreet(null);
		
		verify(streetRepository, times(1)).saveAndFlush(street);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateStreetTest() {
		Street street = new Street();
		Street street2 = new Street();
		
		street.setId(1);
		street.setName("street1");
		street.setCity(city);
		street.setHouses(houses);
		
		street2.setId(2);
		
		addressServiceImpl.addStreet(street);
		
		when(streetRepository.exists(street.getId())).thenReturn(true);
		when(streetRepository.exists(street2.getId())).thenReturn(false);
		when(addressServiceImpl.updateStreet(street)).thenReturn(street);
		
		addressServiceImpl.updateStreet(street);
		addressServiceImpl.updateStreet(street2);
		streetRepository.exists(street.getId());
		when(addressServiceImpl.updateStreet(null));
		
		verify(streetRepository, times(1)).saveAndFlush(street);
	}
	
	@Test
	public void deleteStreetTest() {
		Street street = new Street();
		Osbb osbb = new Osbb();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		street.setId(3);
		street.setName("street1");
		street.setHouses(houses);
		street.setCity(city);
		
		osbb.setOsbbId(1);
		osbb.setAvailable(true);
		osbb.setContracts(null);
		osbb.setCreationDate(timestamp);
		osbb.setDescription("description");
		osbb.setDistrict(district);
		osbb.setEvents(null);
		osbb.setHouseNumber("5");
		osbb.setHouses(houses);
		osbb.setLogo(null);
		osbb.setName("osbb1");
		osbb.setReports(null);
		osbb.setStreet(street);
		osbb.setUsers(null);
		
		osbbServiceImpl.addOsbb(osbb);
		addressServiceImpl.addStreet(street);
		
		when(osbbRepository.findByStreetId(1)).thenReturn(listOfOsbb);
		when(osbbRepository.findByStreetId(2)).thenReturn(listOfOsbb);
		when(osbbRepository.findByStreetId(1).isEmpty()).thenReturn(false);
		when(osbbRepository.findByStreetId(2).isEmpty()).thenReturn(true);
		
		addressServiceImpl.deleteStreet(1);
		addressServiceImpl.deleteStreet(2);
		addressServiceImpl.deleteStreet(3);
		addressServiceImpl.deleteStreet(null);
		
		verify(streetRepository, times(1)).delete(1);
		verify(streetRepository, times(1)).delete(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addDistrictTest() {
		when(addressServiceImpl.addDistrict(district)).thenReturn(district);
		when(addressServiceImpl.addDistrict(null)).thenReturn(district);
		
		addressServiceImpl.addDistrict(district);
		addressServiceImpl.addDistrict(null);
		
		verify(districtRepository, times(1)).saveAndFlush(district);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateDistrictTest() {
		District district = new District();
		District district2 = new District();
		
		district.setId(1);
		district.setName("district1");
		district.setCity(city);
		
		district2.setId(2);
		
		addressServiceImpl.addDistrict(district);
		
		when(districtRepository.exists(district.getId())).thenReturn(true);
		when(districtRepository.exists(district2.getId())).thenReturn(false);
		when(addressServiceImpl.updateDistrict(district)).thenReturn(district);
		
		addressServiceImpl.updateDistrict(district);
		addressServiceImpl.updateDistrict(district2);
		districtRepository.exists(district.getId());
		when(addressServiceImpl.updateDistrict(null));
		
		verify(districtRepository, times(1)).saveAndFlush(district);
	}
	
	@Test
	public void deleteDistrictTest() {
		when(osbbRepository.findByDistrictId(1)).thenReturn(listOfOsbb);
		when(osbbRepository.findByDistrictId(2)).thenReturn(listOfOsbb);
		when(osbbRepository.findByDistrictId(1).isEmpty()).thenReturn(false);
		when(osbbRepository.findByDistrictId(2).isEmpty()).thenReturn(true);
		
		addressServiceImpl.deleteDistrict(1);
		addressServiceImpl.deleteDistrict(2);
		
		verify(districtRepository, times(1)).delete(1);
		verify(districtRepository, times(1)).delete(2);
	}

}
