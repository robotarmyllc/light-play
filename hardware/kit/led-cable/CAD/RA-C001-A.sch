<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE eagle SYSTEM "eagle.dtd">
<eagle version="6.4">
<drawing>
<settings>
<setting alwaysvectorfont="yes"/>
<setting verticaltext="up"/>
</settings>
<grid distance="0.1" unitdist="inch" unit="inch" style="lines" multiple="1" display="no" altdistance="0.01" altunitdist="inch" altunit="inch"/>
<layers>
<layer number="1" name="Top" color="4" fill="1" visible="no" active="no"/>
<layer number="2" name="Route2" color="1" fill="3" visible="no" active="no"/>
<layer number="3" name="Route3" color="4" fill="3" visible="no" active="no"/>
<layer number="4" name="Route4" color="1" fill="4" visible="no" active="no"/>
<layer number="5" name="Route5" color="4" fill="4" visible="no" active="no"/>
<layer number="6" name="Route6" color="1" fill="8" visible="no" active="no"/>
<layer number="7" name="Route7" color="4" fill="8" visible="no" active="no"/>
<layer number="8" name="Route8" color="1" fill="2" visible="no" active="no"/>
<layer number="9" name="Route9" color="4" fill="2" visible="no" active="no"/>
<layer number="10" name="Route10" color="1" fill="7" visible="no" active="no"/>
<layer number="11" name="Route11" color="4" fill="7" visible="no" active="no"/>
<layer number="12" name="Route12" color="1" fill="5" visible="no" active="no"/>
<layer number="13" name="Route13" color="4" fill="5" visible="no" active="no"/>
<layer number="14" name="Route14" color="1" fill="6" visible="no" active="no"/>
<layer number="15" name="Route15" color="4" fill="6" visible="no" active="no"/>
<layer number="16" name="Bottom" color="1" fill="1" visible="no" active="no"/>
<layer number="17" name="Pads" color="2" fill="1" visible="no" active="no"/>
<layer number="18" name="Vias" color="2" fill="1" visible="no" active="no"/>
<layer number="19" name="Unrouted" color="6" fill="1" visible="no" active="no"/>
<layer number="20" name="Dimension" color="15" fill="1" visible="no" active="no"/>
<layer number="21" name="tPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="22" name="bPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="23" name="tOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="24" name="bOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="25" name="tNames" color="7" fill="1" visible="no" active="no"/>
<layer number="26" name="bNames" color="7" fill="1" visible="no" active="no"/>
<layer number="27" name="tValues" color="7" fill="1" visible="no" active="no"/>
<layer number="28" name="bValues" color="7" fill="1" visible="no" active="no"/>
<layer number="29" name="tStop" color="7" fill="3" visible="no" active="no"/>
<layer number="30" name="bStop" color="7" fill="6" visible="no" active="no"/>
<layer number="31" name="tCream" color="7" fill="4" visible="no" active="no"/>
<layer number="32" name="bCream" color="7" fill="5" visible="no" active="no"/>
<layer number="33" name="tFinish" color="6" fill="3" visible="no" active="no"/>
<layer number="34" name="bFinish" color="6" fill="6" visible="no" active="no"/>
<layer number="35" name="tGlue" color="7" fill="4" visible="no" active="no"/>
<layer number="36" name="bGlue" color="7" fill="5" visible="no" active="no"/>
<layer number="37" name="tTest" color="7" fill="1" visible="no" active="no"/>
<layer number="38" name="bTest" color="7" fill="1" visible="no" active="no"/>
<layer number="39" name="tKeepout" color="4" fill="11" visible="no" active="no"/>
<layer number="40" name="bKeepout" color="1" fill="11" visible="no" active="no"/>
<layer number="41" name="tRestrict" color="4" fill="10" visible="no" active="no"/>
<layer number="42" name="bRestrict" color="1" fill="10" visible="no" active="no"/>
<layer number="43" name="vRestrict" color="2" fill="10" visible="no" active="no"/>
<layer number="44" name="Drills" color="7" fill="1" visible="no" active="no"/>
<layer number="45" name="Holes" color="7" fill="1" visible="no" active="no"/>
<layer number="46" name="Milling" color="3" fill="1" visible="no" active="no"/>
<layer number="47" name="Measures" color="7" fill="1" visible="no" active="no"/>
<layer number="48" name="Document" color="7" fill="1" visible="no" active="no"/>
<layer number="49" name="Reference" color="7" fill="1" visible="no" active="no"/>
<layer number="51" name="tDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="52" name="bDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="91" name="Nets" color="2" fill="1" visible="yes" active="yes"/>
<layer number="92" name="Busses" color="1" fill="1" visible="yes" active="yes"/>
<layer number="93" name="Pins" color="2" fill="1" visible="no" active="yes"/>
<layer number="94" name="Symbols" color="4" fill="1" visible="yes" active="yes"/>
<layer number="95" name="Names" color="7" fill="1" visible="yes" active="yes"/>
<layer number="96" name="Values" color="7" fill="1" visible="yes" active="yes"/>
<layer number="97" name="Info" color="7" fill="1" visible="yes" active="yes"/>
<layer number="98" name="Guide" color="6" fill="1" visible="yes" active="yes"/>
</layers>
<schematic xreflabel="%F%N/%S.%C%R" xrefpart="/%S.%C%R">
<libraries>
<library name="frames">
<packages>
</packages>
<symbols>
<symbol name="LETTER_L">
<frame x1="0" y1="0" x2="248.92" y2="185.42" columns="12" rows="17" layer="94" border-left="no" border-top="no" border-right="no" border-bottom="no"/>
</symbol>
<symbol name="DOCFIELD">
<wire x1="0" y1="0" x2="71.12" y2="0" width="0.1016" layer="94"/>
<wire x1="101.6" y1="15.24" x2="87.63" y2="15.24" width="0.1016" layer="94"/>
<wire x1="0" y1="0" x2="0" y2="5.08" width="0.1016" layer="94"/>
<wire x1="0" y1="5.08" x2="71.12" y2="5.08" width="0.1016" layer="94"/>
<wire x1="0" y1="5.08" x2="0" y2="15.24" width="0.1016" layer="94"/>
<wire x1="101.6" y1="15.24" x2="101.6" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="71.12" y2="0" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="87.63" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="0" x2="101.6" y2="0" width="0.1016" layer="94"/>
<wire x1="87.63" y1="15.24" x2="87.63" y2="5.08" width="0.1016" layer="94"/>
<wire x1="87.63" y1="15.24" x2="0" y2="15.24" width="0.1016" layer="94"/>
<wire x1="87.63" y1="5.08" x2="101.6" y2="5.08" width="0.1016" layer="94"/>
<wire x1="101.6" y1="5.08" x2="101.6" y2="0" width="0.1016" layer="94"/>
<wire x1="0" y1="15.24" x2="0" y2="22.86" width="0.1016" layer="94"/>
<wire x1="101.6" y1="35.56" x2="0" y2="35.56" width="0.1016" layer="94"/>
<wire x1="101.6" y1="35.56" x2="101.6" y2="22.86" width="0.1016" layer="94"/>
<wire x1="0" y1="22.86" x2="101.6" y2="22.86" width="0.1016" layer="94"/>
<wire x1="0" y1="22.86" x2="0" y2="35.56" width="0.1016" layer="94"/>
<wire x1="101.6" y1="22.86" x2="101.6" y2="15.24" width="0.1016" layer="94"/>
<text x="1.27" y="1.27" size="2.54" layer="94" font="vector">Date:</text>
<text x="12.7" y="1.27" size="2.54" layer="94" font="vector">&gt;LAST_DATE_TIME</text>
<text x="72.39" y="1.27" size="2.54" layer="94" font="vector">Sheet:</text>
<text x="86.36" y="1.27" size="2.54" layer="94" font="vector">&gt;SHEET</text>
<text x="88.9" y="11.43" size="2.54" layer="94" font="vector">REV:</text>
<text x="1.27" y="19.05" size="2.54" layer="94" font="vector">TITLE:</text>
<text x="1.27" y="11.43" size="2.54" layer="94" font="vector">Document Number:</text>
<text x="17.78" y="19.05" size="2.54" layer="94" font="vector">&gt;DRAWING_NAME</text>
</symbol>
</symbols>
<devicesets>
<deviceset name="LETTER_L" prefix="FRAME" uservalue="yes">
<description>&lt;b&gt;FRAME&lt;/b&gt;&lt;p&gt;
LETTER landscape</description>
<gates>
<gate name="G$1" symbol="LETTER_L" x="0" y="0"/>
<gate name="G$2" symbol="DOCFIELD" x="147.32" y="0" addlevel="must"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="robicon-cables">
<packages>
<package name="TERMINAL_GENERIC">
<pad name="1" x="0" y="0" drill="0.9652" shape="square"/>
</package>
</packages>
<symbols>
<symbol name="HARWIN_M20_CRIMP_TERMINAL">
<wire x1="-3.048" y1="-1.524" x2="1.27" y2="-1.524" width="0.254" layer="94"/>
<wire x1="1.27" y1="-1.524" x2="1.524" y2="-1.524" width="0.127" layer="94"/>
<wire x1="1.524" y1="-1.524" x2="1.778" y2="-1.524" width="0.254" layer="94"/>
<wire x1="1.778" y1="-1.524" x2="2.032" y2="-1.524" width="0.127" layer="94"/>
<wire x1="2.032" y1="-1.524" x2="8.382" y2="-1.524" width="0.254" layer="94"/>
<wire x1="8.382" y1="-1.524" x2="11.684" y2="-1.524" width="0.254" layer="94"/>
<wire x1="11.684" y1="-1.524" x2="13.462" y2="-1.524" width="0.254" layer="94"/>
<wire x1="13.462" y1="-1.524" x2="13.462" y2="1.016" width="0.254" layer="94"/>
<wire x1="13.462" y1="1.016" x2="13.462" y2="1.27" width="0.254" layer="94"/>
<wire x1="13.462" y1="1.27" x2="11.684" y2="1.27" width="0.254" layer="94"/>
<wire x1="11.684" y1="1.27" x2="11.684" y2="1.016" width="0.254" layer="94"/>
<wire x1="11.684" y1="1.016" x2="11.684" y2="0.508" width="0.254" layer="94"/>
<wire x1="11.684" y1="0.508" x2="9.906" y2="0.508" width="0.254" layer="94"/>
<wire x1="9.906" y1="0.508" x2="7.874" y2="0.508" width="0.254" layer="94"/>
<wire x1="7.874" y1="0.508" x2="7.874" y2="1.016" width="0.254" layer="94"/>
<wire x1="7.874" y1="1.016" x2="7.874" y2="1.27" width="0.254" layer="94"/>
<wire x1="7.874" y1="1.27" x2="6.604" y2="1.27" width="0.254" layer="94"/>
<wire x1="6.604" y1="1.27" x2="6.604" y2="0.254" width="0.254" layer="94"/>
<wire x1="6.604" y1="0.254" x2="6.096" y2="0.254" width="0.254" layer="94"/>
<wire x1="6.096" y1="0.254" x2="6.096" y2="0.762" width="0.254" layer="94"/>
<wire x1="6.096" y1="0.762" x2="5.08" y2="0.508" width="0.254" layer="94"/>
<wire x1="5.08" y1="0.508" x2="5.08" y2="0" width="0.254" layer="94"/>
<wire x1="5.08" y1="0" x2="2.794" y2="-0.508" width="0.254" layer="94"/>
<wire x1="2.794" y1="-0.508" x2="2.54" y2="0.762" width="0.254" layer="94"/>
<wire x1="2.54" y1="0.762" x2="2.286" y2="1.016" width="0.254" layer="94"/>
<wire x1="2.286" y1="1.016" x2="0.508" y2="1.016" width="0.254" layer="94"/>
<wire x1="0.508" y1="1.016" x2="0.508" y2="0.762" width="0.254" layer="94"/>
<wire x1="0.508" y1="0.762" x2="0.508" y2="-1.016" width="0.254" layer="94"/>
<wire x1="0" y1="-0.508" x2="-0.254" y2="-0.508" width="0.254" layer="94"/>
<wire x1="0.508" y1="-1.016" x2="-0.762" y2="-1.016" width="0.254" layer="94"/>
<wire x1="-0.762" y1="-1.016" x2="-2.032" y2="1.524" width="0.254" layer="94"/>
<wire x1="-2.032" y1="1.524" x2="-3.048" y2="1.524" width="0.254" layer="94"/>
<wire x1="-3.048" y1="1.524" x2="-3.048" y2="-1.524" width="0.254" layer="94"/>
<wire x1="1.524" y1="-1.524" x2="1.524" y2="0" width="0.127" layer="94"/>
<wire x1="1.524" y1="0" x2="1.27" y2="0" width="0.127" layer="94"/>
<wire x1="1.27" y1="0" x2="1.27" y2="-1.524" width="0.127" layer="94"/>
<wire x1="2.032" y1="-1.524" x2="2.032" y2="0" width="0.127" layer="94"/>
<wire x1="2.032" y1="0" x2="1.778" y2="0" width="0.127" layer="94"/>
<wire x1="1.778" y1="0" x2="1.778" y2="-1.524" width="0.127" layer="94"/>
<wire x1="0.508" y1="0.762" x2="2.54" y2="0.762" width="0.127" layer="94"/>
<wire x1="7.874" y1="1.016" x2="10.16" y2="1.016" width="0.254" layer="94"/>
<wire x1="11.684" y1="1.016" x2="9.906" y2="0.508" width="0.127" layer="94"/>
<wire x1="8.382" y1="-1.524" x2="8.382" y2="-1.27" width="0.127" layer="94"/>
<wire x1="8.382" y1="-1.27" x2="11.684" y2="-1.27" width="0.127" layer="94"/>
<wire x1="11.684" y1="-1.27" x2="11.684" y2="-1.524" width="0.127" layer="94"/>
<wire x1="11.684" y1="1.016" x2="13.462" y2="1.016" width="0.127" layer="94"/>
<text x="-5.08" y="2.54" size="2.54" layer="95" ratio="12">&gt;NAME</text>
<text x="-5.08" y="-4.572" size="1.6764" layer="96">&gt;VALUE</text>
<pin name="1" x="-5.08" y="0" visible="off" length="point" direction="pas"/>
<polygon width="0.254" layer="94">
<vertex x="-3.048" y="1.016"/>
<vertex x="-5.588" y="1.016"/>
<vertex x="-5.9472" y="0.6568" curve="44.999323"/>
<vertex x="-6.096" y="0.2976"/>
<vertex x="-6.096" y="0.2104" curve="44.999323"/>
<vertex x="-5.9472" y="-0.1488" curve="-90"/>
<vertex x="-5.9472" y="-0.8672"/>
<vertex x="-6.096" y="-1.016"/>
<vertex x="-3.048" y="-1.016"/>
</polygon>
<polygon width="0.254" layer="94">
<vertex x="-1.778" y="1.016"/>
<vertex x="0" y="1.016"/>
<vertex x="0" y="-1.016"/>
<vertex x="-0.762" y="-1.016"/>
</polygon>
<polygon width="0.127" layer="94" spacing="0.3048" pour="hatch">
<vertex x="0" y="0.508"/>
<vertex x="0.508" y="0.508"/>
<vertex x="0.508" y="-0.508"/>
<vertex x="0" y="-0.508"/>
</polygon>
<polygon width="0.127" layer="94" spacing="0.3048" pour="hatch">
<vertex x="2.54" y="0.508"/>
<vertex x="3.302" y="0.508"/>
<vertex x="3.302" y="-0.508"/>
<vertex x="2.794" y="-0.508"/>
</polygon>
</symbol>
</symbols>
<devicesets>
<deviceset name="HARWIN_M20_2.54MM_CONTACT" prefix="P" uservalue="yes">
<description>Harwin 0.1" Female Crimp Terninal</description>
<gates>
<gate name="G$1" symbol="HARWIN_M20_CRIMP_TERMINAL" x="-2.54" y="0"/>
</gates>
<devices>
<device name="" package="TERMINAL_GENERIC">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
</connects>
<technologies>
<technology name="">
<attribute name="VALUE" value="HARWIN_M20_1160042" constant="no"/>
</technology>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
</libraries>
<attributes>
</attributes>
<variantdefs>
</variantdefs>
<classes>
<class number="0" name="default" width="0" drill="0">
</class>
</classes>
<parts>
<part name="FRAME1" library="frames" deviceset="LETTER_L" device="" value="RA-C001-A">
<attribute name="DOCUMENT_NUMBER" value="RA-C001-A"/>
</part>
<part name="CT1" library="robicon-cables" deviceset="HARWIN_M20_2.54MM_CONTACT" device="" value="HARWIN_M20_1160042"/>
<part name="CT2" library="robicon-cables" deviceset="HARWIN_M20_2.54MM_CONTACT" device="" value="HARWIN_M20_1160042"/>
</parts>
<sheets>
<sheet>
<plain>
<wire x1="49.53" y1="154.94" x2="49.53" y2="160.02" width="0.1524" layer="97"/>
<wire x1="49.53" y1="160.02" x2="49.53" y2="165.1" width="0.1524" layer="97"/>
<wire x1="49.53" y1="160.02" x2="81.28" y2="160.02" width="0.1524" layer="97"/>
<wire x1="176.53" y1="154.94" x2="176.53" y2="160.02" width="0.1524" layer="97"/>
<wire x1="176.53" y1="160.02" x2="176.53" y2="165.1" width="0.1524" layer="97"/>
<wire x1="176.53" y1="160.02" x2="137.16" y2="160.02" width="0.1524" layer="97"/>
<wire x1="74.93" y1="110.49" x2="74.93" y2="105.41" width="0.4064" layer="97"/>
<wire x1="74.93" y1="105.41" x2="74.93" y2="92.71" width="0.4064" layer="97"/>
<wire x1="74.93" y1="92.71" x2="74.93" y2="87.63" width="0.4064" layer="97"/>
<wire x1="74.93" y1="87.63" x2="88.9" y2="87.63" width="0.4064" layer="97"/>
<wire x1="88.9" y1="87.63" x2="154.94" y2="87.63" width="0.4064" layer="97"/>
<wire x1="154.94" y1="87.63" x2="154.94" y2="92.71" width="0.4064" layer="97"/>
<wire x1="154.94" y1="92.71" x2="154.94" y2="105.41" width="0.4064" layer="97"/>
<wire x1="154.94" y1="105.41" x2="154.94" y2="110.49" width="0.4064" layer="97"/>
<wire x1="154.94" y1="110.49" x2="110.49" y2="110.49" width="0.4064" layer="97"/>
<wire x1="110.49" y1="110.49" x2="74.93" y2="110.49" width="0.4064" layer="97"/>
<wire x1="74.93" y1="92.71" x2="154.94" y2="92.71" width="0.1524" layer="97"/>
<wire x1="74.93" y1="105.41" x2="88.9" y2="105.41" width="0.4064" layer="97"/>
<wire x1="88.9" y1="105.41" x2="154.94" y2="105.41" width="0.4064" layer="97"/>
<wire x1="88.9" y1="105.41" x2="88.9" y2="87.63" width="0.1524" layer="97"/>
<wire x1="110.49" y1="110.49" x2="110.49" y2="140.97" width="0.1524" layer="97"/>
<wire x1="157.48" y1="107.95" x2="157.48" y2="102.87" width="0.4064" layer="97"/>
<wire x1="157.48" y1="102.87" x2="157.48" y2="83.82" width="0.4064" layer="97"/>
<wire x1="157.48" y1="83.82" x2="218.44" y2="83.82" width="0.4064" layer="97"/>
<wire x1="218.44" y1="83.82" x2="218.44" y2="102.87" width="0.4064" layer="97"/>
<wire x1="218.44" y1="102.87" x2="218.44" y2="107.95" width="0.4064" layer="97"/>
<wire x1="218.44" y1="107.95" x2="157.48" y2="107.95" width="0.4064" layer="97"/>
<wire x1="157.48" y1="102.87" x2="218.44" y2="102.87" width="0.4064" layer="97"/>
<wire x1="181.61" y1="107.95" x2="181.61" y2="140.97" width="0.1524" layer="97"/>
<text x="83.82" y="158.75" size="2.54" layer="97">320mm (+/- 1mm) [12.6in.]</text>
<text x="90.17" y="88.9" size="2.54" layer="97">28-30 AWG</text>
<text x="90.17" y="104.14" size="2.54" layer="97" align="top-left">AWM 1007 or equiv.
Flexible Stranded Wire
Color: Black</text>
<text x="87.63" y="88.9" size="2.54" layer="97" rot="MR0">Gauge</text>
<text x="87.63" y="101.6" size="2.54" layer="97" rot="MR0">Type</text>
<text x="76.2" y="106.68" size="2.54" layer="97" ratio="16">Wire</text>
<text x="148.59" y="38.1" size="5.08" layer="97" ratio="16">Robot Army</text>
<text x="148.59" y="29.21" size="4.572" layer="97" ratio="15">0.1" Header jumpers 320mm</text>
<text x="3.81" y="2.54" size="2.54" layer="97">Copyright 2014 - Robot Army LLC</text>
<text x="158.75" y="99.06" size="2.54" layer="97">Harwin PN:  M20-1160042</text>
<text x="160.02" y="85.09" size="1.778" layer="97">Crimp per Manufacturer Recommendation</text>
<text x="158.75" y="104.14" size="2.54" layer="97" ratio="16">Crimp Terminal</text>
<text x="176.53" y="93.98" size="2.54" layer="97">or  M20-1180042</text>
<text x="176.53" y="88.9" size="2.54" layer="97">or equivalent</text>
<polygon width="0.1524" layer="97">
<vertex x="49.53" y="160.02"/>
<vertex x="52.07" y="160.782"/>
<vertex x="52.07" y="159.258"/>
</polygon>
<polygon width="0.1524" layer="97">
<vertex x="176.53" y="160.02"/>
<vertex x="173.99" y="160.782"/>
<vertex x="173.99" y="159.258"/>
</polygon>
<polygon width="0.1524" layer="97">
<vertex x="110.49" y="143.51"/>
<vertex x="109.22" y="139.7"/>
<vertex x="111.76" y="139.7"/>
</polygon>
<polygon width="0.1524" layer="97">
<vertex x="44.45" y="140.97"/>
<vertex x="43.18" y="137.16"/>
<vertex x="45.72" y="137.16"/>
</polygon>
<polygon width="0.1524" layer="97">
<vertex x="181.61" y="140.97"/>
<vertex x="180.34" y="137.16"/>
<vertex x="182.88" y="137.16"/>
</polygon>
<wire x1="12.7" y1="105.41" x2="12.7" y2="100.33" width="0.4064" layer="97"/>
<wire x1="12.7" y1="100.33" x2="12.7" y2="81.28" width="0.4064" layer="97"/>
<wire x1="12.7" y1="81.28" x2="73.66" y2="81.28" width="0.4064" layer="97"/>
<wire x1="73.66" y1="81.28" x2="73.66" y2="100.33" width="0.4064" layer="97"/>
<wire x1="73.66" y1="100.33" x2="73.66" y2="105.41" width="0.4064" layer="97"/>
<wire x1="73.66" y1="105.41" x2="44.45" y2="105.41" width="0.4064" layer="97"/>
<wire x1="44.45" y1="105.41" x2="12.7" y2="105.41" width="0.4064" layer="97"/>
<wire x1="12.7" y1="100.33" x2="73.66" y2="100.33" width="0.4064" layer="97"/>
<wire x1="44.45" y1="105.41" x2="44.45" y2="138.43" width="0.1524" layer="97"/>
<text x="13.97" y="96.52" size="2.54" layer="97">Harwin PN:  M20-1160042</text>
<text x="15.24" y="82.55" size="1.778" layer="97">Crimp per Manufacturer Recommendation</text>
<text x="13.97" y="101.6" size="2.54" layer="97" ratio="16">Crimp Terminal</text>
<text x="31.75" y="91.44" size="2.54" layer="97">or  M20-1180042</text>
<text x="31.75" y="86.36" size="2.54" layer="97">or equivalent</text>
<text x="81.28" y="165.1" size="3.81" layer="97">Wire Color:   Black</text>
</plain>
<instances>
<instance part="FRAME1" gate="G$1" x="0" y="0"/>
<instance part="FRAME1" gate="G$2" x="147.32" y="0">
<attribute name="DOCUMENT_NUMBER" x="182.88" y="7.62" size="3.81" layer="96"/>
</instance>
<instance part="CT1" gate="G$1" x="45.72" y="147.32" rot="MR0"/>
<instance part="CT2" gate="G$1" x="180.34" y="147.32"/>
</instances>
<busses>
</busses>
<nets>
<net name="N$1" class="0">
<segment>
<wire x1="175.26" y1="147.32" x2="50.8" y2="147.32" width="2.286" layer="91"/>
<pinref part="CT1" gate="G$1" pin="1"/>
<pinref part="CT2" gate="G$1" pin="1"/>
</segment>
</net>
</nets>
</sheet>
</sheets>
</schematic>
</drawing>
<compatibility>
<note version="6.3" minversion="6.2.2" severity="warning">
Since Version 6.2.2 text objects can contain more than one line,
which will not be processed correctly with this version.
</note>
</compatibility>
</eagle>
