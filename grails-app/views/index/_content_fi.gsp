<%
	def company = grailsApplication.mainContext.getBean("companyConfiguration")
%>
<div class="content" id="home">
		<div class="container">
			<h1 class="center">Mittaa ja johda elinkaari- ja ympäristötehokkuutta</h1>
          	
			<p class="center">Verkkopalvelu, jolla voit laskea ja optimoida rakentamisen ja kiinteistöjen kestävyysmittareita</p>
			<div class="banner">
				<div class="slides">

					<!-- >> SLIDE >> -->
					<div class="slide">
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_schedule_small.png" alt="" /></a>
							<h2>Faktoilla päätöksiä</h2>
							<p>Luotettavaa, numeerista tietoa suorituskyvystä ja sen parantamiskeinoista.</p>
						</div>
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_play_small.png" alt="" /></a>
							<h2>Helppo ja tehokas</h2>
							<p>Intuitiivinen käyttöliittymä tuottaa nopeasti tuloksia. Tiedot ovat helposti jaettavissa.</p>
						</div>
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_24h_small.png" alt="" /></a>
							<h2>Missä vain, milloin vain</h2>
							<p>Ei asentamista, ei rajoituksia eikä sähläystä. Tiedot ovat aina ajan tasalla ja saatavissa.</p>
						</div>
					</div>
					<!-- << SLIDE << -->

					<!-- >> SLIDE >> -->
					<div class="slide">
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_rattaat_small.png" alt="" /></a>
							<h2>Teollinen mittakaava</h2>
							<p>Skaalautuva ratkaisu, joka tekee suurten tietomäärien hallinnoinnista helppoa.</p>
						</div>
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_plugin_small.png" alt="" /></a>
							<h2>Integraatiovalmiudet</h2>
							<p>Pystymme siirtämään tiedot olemassa olevista järjestelmistäsi saumattomasti.</p>
						</div>
						<div class="item">
							<a href="#benefits"><asset:image src="img/360_compass_small.png" alt="" /></a>
							<h2>Perustuu standardeihin</h2>
							<p>
								Kansainväliset ja kansalliset standardit takaavat luotettavuuden ja läpinäkyvyyden.
							</p>
						</div>
					</div>
					<!-- << SLIDE << -->

				</div>
				<div class="clear"></div>
				<div class="fade left"></div>
				<div class="fade right"></div>
				<div class="banner-pager"></div>
			</div>
			<div class="slide-controls">
				<div class="container">
					<a href="#" class="slide-to" data-target=".slides" data-cycle="prev"></a>
					<a href="#" class="slide-to" data-target=".slides" data-cycle="next"></a>
				</div>
			</div>
				
			<div class="chapter">
  			    <p style="text-align: center;">
				  <opt:link class="special-btn" action="form"><span class="btn">Rekisteröidy</span></opt:link>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Se on ilmaista ja vie alle minuutin. Tai <a href="#overview">tutustu palveluun</a> tarkemmin.						
				</p>
			</div>	
			
			<div class="chapter" id="overview">
			
			<h2>Tunnusluvuilla tuloksellisuutta toimintaan</h2>
				<div class="half">
					<div class="half">
						<p>
							360optimi on verkkosovellus, joka tekee elinkaaritehokkuuden mittaamisesta ja tarvittavien tietojen hallinnasta helppoa, nopeaa ja yhteistoiminnallista.
						</p>
						<p>
							Tuloksellinen päätöksenteko vaatii taloudellisten, sosiaalisten ja ympäristövaikutusten sekä muiden tavoitteiden tasapainottamista. Oikean tasapainon löytäminen vaatii kokonaiskuvaa – 360&deg; näkymää.
						</p>
					</div>
					<div class="half">
						<p>
							360optimi tukee johtamista rakennuksen elinkaaren kaikissa vaiheissa aina suunnittelusta ja kiinteistöjen käytöstä remontoinnin ja purkamisen hyötyjen ja haittojen vertailuun asti.
						</p>
						<p>
							360optimi auttaa omistajia ja managereita seuraamaan suorituskykyä ja tunnistamaan kehittämismahdollisuuksia.
						</p>
					</div>
				</div>
				<div class="half">
					<asset:image src="img/main_screen_images.png" alt="" />
				</div>
				<div class="clear"></div>
			</div>
			<hr>


			<div class="chapter" id="references">
			<h2>Referenssit - valikoituja esimerkkejä</h2>
				<div class="half">
					<div class="half">
						<p>
							<b>Ilmastoystävällinen kerrostalo 2013-kilpailu</b> yhdessä Rakennusteollisuus RT ry:n ja Green Building Council Finlandin kanssa
							haastaa rakentajat toimimaan vähähiilisemmin - ja osoittamaan sen.
						</p>
						<p>
							<b>Esteettömyysmittari</b> asuinrakennuksille auttaa visualisoimaan hissin vaikutuksia asukkaiden liikkumiseen.
							Työkalu on kehitetty LADEC Oy:n toimeksiannosta osana jälkiasennushissien asentamisen edistämishankketta.
						</p>
					</div>
					<div class="half">
						<p>
							<b>Tontinluovutuskilpailu</b> Porvoossa kannustaa tarjoajia suunnittelemaan vähähiilisiä ja elinkaaritehokkaita ratkaisuja.
							360optimin avulla tarjoukset voidaan arvioida samoilla pelisäännöillä ja paras voittaa.
						</p>
						<p>
							<b>CO2-päästöseuranta</b> auttaa Tampere-taloa keskittymään toimenpiteisiin, joilla on suurin merkitys haitallisten ympäristövaikutusten vähentämisessä sekä
							kiinteistönpidossa että ravintolatoiminnassa.
						</p>
					</div>
				</div>
				<div class="half">
					<p><b>Olemme ylpeitä saada palvella seuraavia organisaatioita:</b></p>
					<asset:image src="img/reference_logos.png" alt="Reference customers" />
				</div>
				<div class="clear"></div>
			</div>
			<hr>




			<div class="chapter" id="benefits">
				<h2>Ominaisuudet & hyödyt</h2>
				<div class="featureset">
					<div class="half">
						<div class="half">
							<asset:image src="img/360_check_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Faktoilla päätöksiä</h3>
							<p>Tarjoamme toiminnan kehittämisen mittaristoja useille sektoreille:</p>
						<ul>
							<li>Kiinteistöt</li>
							<li>Rakentaminen</li>
							<li>Ympäristöraportointi</li>
							<li>HoReCa-toimiala</li>
						</ul>
						</div>
					</div>
					<div class="half">
						<div class="half">
							<asset:image src="img/360_play_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Helppo ja tehokas</h3>
							<p>
								Mittareita useisiin tarpeisiin:</p>

						<ul>
							<li>Suorituskyvyn seuranta</li>
							<li>Parannusten arvioiminen</li>
							<li>Tarjousten vertailu</li>
							<li>Tavoitteiden asettaminen ja tulosten todentaminen</li>
							<li>Portfolioiden analysointi</li>
						</ul>

						</div>
					</div>
					<div class="clear"></div>
				</div>


				<div class="featureset">
					<div class="half">
						<div class="half">
							<asset:image src="img/360_balance_small.png" alt="" />
						</div>
						<div class="half">
						<h3>Joustava ja laajennettava</h3>
							<p>Räätälöityjä ja valmiita ratkaisuja:</p>
							<ul>
							<li>Hiilijalanjäljet</li>
							<li>Elinkaariarviointi</li>
							<li>Elinkaarikustannus</li>
							<li>Energiatehokkuus</li>
							<li>Räätälöidyt mittarit</li>
							<li>Ja paljon muuta</li>
						</ul>
						</div>
					</div>
					<div class="half">
						<div class="half">
							<asset:image src="img/360_schedule_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Kattava tietokanta</h3>
							<p>Korkealaatuista, standardoituihin ympäristöselosteisiin perustuvaa tietoa, joka kattaa rakennusmateriaalit, energiaratkaisut, huolto- ja ylläpitotoimenpiteet, tarvikkeet, foodservice-toiminnan jne.</p>
						</div>
					</div>
					<div class="clear"></div>
				</div>


				<div class="featureset">
					<div class="half">
						<div class="half">
							<asset:image src="img/360_24h_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Missä vain, milloin vain</h3>
							<p>Suorituskyvyn mittaamista niin kuin se on tarkoitettu. Ei asentamista, ei rajoituksia eikä sähläystä. Tiedot ovat aina ajan tasalla ja saatavissa. Sinun tarvitsee vain avata selaimesi.</p>
						</div>
					</div>
					<div class="half">
						<div class="half">
							<asset:image src="img/360_rattaat_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Teollinen mittakaava</h3>
							<p>Skaalautuva ratkaisu, joka tekee suurten tietomäärien hallinnoinnista helppoa. Työkalut ja tuki ovat aina saatavissa.</p>

						</div>
					</div>
					<div class="clear"></div>
				</div>


				<div class="featureset">
					<div class="half">
						<div class="half">
							<asset:image src="img/360_plugin_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Integraatiovalmiudet</h3>
							<p>Meillä on valmiudet integroida tietoja saumattomasti olemassa olevista järjestelmistä. Näin säästämme arvokkainta resurssiasi: aikaa. </p>
						</div>
					</div>
					<div class="half">
						<div class="half">
							<asset:image src="img/360_compass_small.png" alt="" />
						</div>
						<div class="half">
							<h3>Perustuu standardeihin</h3>
							<p>Perustuu kansainvälisiin ja kansallisiin standardeihin:</p>
						<ul>
							<li>EN 15978 & EN 16627</li>
							<li>LEED & BREEAM</li>
							<li>ISO 14040</li>
							<li>GHG Protocol</li>
                            <li>GBC Finland</li>
						</ul>

						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>

			<hr>

			<div class="chapter" id="contacts">
				<h2>Ota yhteyttä</h2>
				<div class="half">
					<div class="half">
						<p>
							360optimi on saatavana maksullisena verkkopalveluna kaikille käyttäjille,
							joko sellaisenaan tai yhdistettynä tuki- ja konsultaatiopalveluihimme.
						</p>
					</div>
					<div class="half">
						<p>
							360optimi on ohjelmisto, jonka on kehittänyt <a href="${company.url}">${company.name}</a>.
						</p>
						<p>
							Kaupalliset tiedustelut: Panu Pasanen, +358 44 2871 722.
						</p>
<!--						<p>
							<a class="special-btn" href="${company.url}">
								<span class="btn">Contact us</span>
							</a>
						</p> -->
					</div>
				</div>

				<div class="half">
					<div class="half">
						<p>
							Tuotetiedot ja tekninen tuki: kirjoita sähköpostia osoitteella support (at) "${company.domainName}".
						</p>
						<p>
							Muut yhteystietomme löytyvät sivustoltamme <a href="${company.url}/yhteystiedot.html">${company.domainName}.</a>
						</p>
					</div>
					<div class="half">
						<p>
						<a href="${company.url}">
						<asset:image src="img/bionovaconsulting.png" alt="" />
						</a></p>
					</div>
				</div>
				<div class="clear"></div>
			</div>


		</div>
	</div>

	<div class="footer">
		<div class="container">
			<div class="footer-column">
				<h3>360optimi</h3>
				<p>
					ohjelmiston on kehittänyt<br />
					<a href="${company.url}">${company.name}</a>
				</p>
			</div>
			<div class="footer-column">
				<h3>Ota yhteyttä</h3>
				<p>
					<a href="${company.name}/yhteystiedot.html">Ota yhteyttä, ja <br>keskustellaan tarpeistanne</a>
				</p>
			</div>
			<div class="footer-column pull-right disabled">
				<p>
                    360optimi &copy; ${company.name}<br />
					Kaikki oikeudet pidätetään.
				</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>