<%
    def company = grailsApplication.mainContext.getBean("companyConfiguration")
%>
<div class="accessibility_lower">
  <p>
    Arviointityökalu on tehty osana Hissiesteetön Suomi 2017 - hanketta. Työkalun avulla voidaan arvioida rakennuksen liikkumisesteettömyyttä erityisesti jälkiasennushissin näkökulmasta. 
    Tavoitteena on auttaa rakennusten käyttäjiä ja isännöitsijöitä arvioimaan rakennuksen esteettömyyden tilaa ja tarvittavia parannustoimenpiteitä. 
    Lisäksi työkalun avulla kerätään tietoa suomalaisten asuinrakennusten tilasta. Työkalu on laadittu Lahden tiede- ja yrityspuiston tarpeisiin.
    Työkalun on toteuttanut ${company.name}.
  </p>
  <p>
    Arviointikriteeristön laadinnassa hyödynnettiin Invalidiliiton, Kynnys Ry:n ja Vanhustyön keskusliiton esteettömyysasiantuntijoita. 
    Lisäksi arviointikriteeristön pohjana on käytetty seuraavia lähteitä:
    <ul>
      <li>Ympäristöministeriön asetus esteettömästä rakennuksesta (F1) ja Ympäristöministeriön asetus asuntosuunnittelusta (G1)</li>
      <li>ISO:n standardi 21542 Building construction — Accessibility and usability of the built environment</li>
      <li>Rakennetun ympäristön esteettömyyskartoitus – Opas kartoituksen tilaajalle ja toteuttajalle, ESKEH arviointimenetelmä (Invalidiliitto Ry, 2009)</li>
      <li>ARVI – asunnon arviointimenetelmä (ARA)</li>
      <li>SURAKU – Esteettömyyskriteerit (Helsingin kaupungin rakennusvirasto, 2008).</li>
    </ul>
    Lisätietoja: <a href="http://www.hissiin.fi" target="_blank">http://www.hissiin.fi</a>
  </p>
  <p>
    <img alt="" src="/app/assets/results/Logot_lowerTemplate.png" />
  </p> 
</div>