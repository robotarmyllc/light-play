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
<library name="frames-custom">
<description>&lt;b&gt;Circuit Monkey Frames for Sheet and Layout&lt;/b&gt;</description>
<packages>
</packages>
<symbols>
<symbol name="LETTER_L">
<frame x1="0" y1="0" x2="248.92" y2="185.42" columns="12" rows="17" layer="94" border-left="no" border-top="no" border-right="no" border-bottom="no"/>
</symbol>
<symbol name="DOCFIELD-LIGHTPLAY">
<description>LightPlay Custom Frame</description>
<wire x1="71.12" y1="0" x2="142.24" y2="0" width="0.1016" layer="94"/>
<wire x1="172.72" y1="15.24" x2="158.75" y2="15.24" width="0.1016" layer="94"/>
<wire x1="71.12" y1="0" x2="71.12" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="142.24" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="71.12" y2="15.24" width="0.1016" layer="94"/>
<wire x1="172.72" y1="15.24" x2="172.72" y2="5.08" width="0.1016" layer="94"/>
<wire x1="142.24" y1="5.08" x2="142.24" y2="0" width="0.1016" layer="94"/>
<wire x1="142.24" y1="5.08" x2="158.75" y2="5.08" width="0.1016" layer="94"/>
<wire x1="142.24" y1="0" x2="172.72" y2="0" width="0.1016" layer="94"/>
<wire x1="158.75" y1="15.24" x2="158.75" y2="5.08" width="0.1016" layer="94"/>
<wire x1="158.75" y1="15.24" x2="71.12" y2="15.24" width="0.1016" layer="94"/>
<wire x1="158.75" y1="5.08" x2="172.72" y2="5.08" width="0.1016" layer="94"/>
<wire x1="172.72" y1="5.08" x2="172.72" y2="0" width="0.1016" layer="94"/>
<wire x1="71.12" y1="0" x2="0" y2="0" width="0.1016" layer="94"/>
<wire x1="0" y1="0" x2="0" y2="5.08" width="0.1016" layer="94"/>
<wire x1="0" y1="5.08" x2="0" y2="15.24" width="0.1016" layer="94"/>
<wire x1="0" y1="15.24" x2="71.12" y2="15.24" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="0" y2="5.08" width="0.1016" layer="94"/>
<text x="72.39" y="1.27" size="2.54" layer="94" font="vector">Date:</text>
<text x="83.82" y="1.27" size="2.54" layer="94" font="vector">&gt;LAST_DATE_TIME</text>
<text x="143.51" y="1.27" size="2.54" layer="94" font="vector">Sheet:</text>
<text x="157.48" y="1.27" size="2.54" layer="94" font="vector">&gt;SHEET</text>
<text x="160.02" y="11.938" size="2.54" layer="94" font="vector">REV:</text>
<text x="1.27" y="1.27" size="2.54" layer="94" font="vector">TITLE:</text>
<text x="72.39" y="11.938" size="2.54" layer="94" font="vector">Document Number:</text>
<text x="15.24" y="1.27" size="2.54" layer="94" font="vector" ratio="15">&gt;DRAWING_NAME</text>
<text x="5.08" y="8.89" size="4.572" layer="94" ratio="15">Robot Army LLC</text>
<text x="5.08" y="6.35" size="1.778" layer="94">Robot-Army.com</text>
<text x="83.82" y="6.096" size="3.81" layer="94" ratio="15">&gt;DOCUMENT_NUMBER</text>
<text x="166.116" y="6.096" size="3.81" layer="94" ratio="15" align="bottom-center">&gt;REV</text>
</symbol>
</symbols>
<devicesets>
<deviceset name="LETTER_L-LIGHTPLAY">
<description>Custom Frame for Sarah's LightPlay projects</description>
<gates>
<gate name="G$1" symbol="LETTER_L" x="0" y="0"/>
<gate name="G$2" symbol="DOCFIELD-LIGHTPLAY" x="76.2" y="0"/>
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
<library name="led">
<description>LEDs Custom</description>
<packages>
<package name="OSRAM_LRTD">
<description>OSRAM LRTD RGB LED Package</description>
<smd name="KR" x="-1.55" y="-1.6" dx="2" dy="1.35" layer="1" roundness="20" rot="R90"/>
<wire x1="-2.35" y1="1.6" x2="2.35" y2="1.6" width="0.127" layer="51"/>
<wire x1="2.35" y1="1.6" x2="2.35" y2="-1.6" width="0.127" layer="51"/>
<wire x1="2.35" y1="-1.6" x2="-2.35" y2="-1.6" width="0.127" layer="51"/>
<wire x1="-2.35" y1="-1.6" x2="-2.35" y2="1.6" width="0.127" layer="51"/>
<smd name="KB" x="1.55" y="-1.6" dx="2" dy="1.35" layer="1" roundness="20" rot="R90"/>
<smd name="AR" x="-1.55" y="1.2" dx="2.9" dy="1.35" layer="1" roundness="20" rot="R90"/>
<smd name="AB" x="1.55" y="1.2" dx="2.9" dy="1.35" layer="1" roundness="20" rot="R90"/>
<smd name="KG" x="0" y="-1.15" dx="2.9" dy="1" layer="1" roundness="20" rot="R270"/>
<smd name="AG" x="0" y="1.65" dx="2" dy="1" layer="1" roundness="20" rot="R270"/>
<circle x="-3" y="-2" radius="0.2" width="0.4" layer="21"/>
<rectangle x1="2.4" y1="-1.5" x2="2.8" y2="1.5" layer="21"/>
<rectangle x1="-2.8" y1="-1.5" x2="-2.4" y2="1.5" layer="21"/>
<text x="-3" y="-1" size="1.27" layer="25" font="fixed" ratio="15" rot="R90">&gt;NAME</text>
<text x="3.5" y="-1.5" size="0.4064" layer="27" font="fixed" rot="R90">&gt;VALUE</text>
</package>
<package name="PLCC-6">
<description>PLCC-6</description>
<wire x1="-2.5" y1="2.5" x2="2.5" y2="2.5" width="0.4064" layer="21"/>
<wire x1="2.5" y1="-2.5" x2="-2.5" y2="-2.5" width="0.4064" layer="21"/>
<wire x1="-2.5" y1="2.5" x2="2.5" y2="2.5" width="0.127" layer="51"/>
<wire x1="2.5" y1="2.5" x2="2.5" y2="-2.5" width="0.127" layer="51"/>
<wire x1="2.5" y1="-2.5" x2="-2.5" y2="-2.5" width="0.127" layer="51"/>
<wire x1="-2.5" y1="-2.5" x2="-2.5" y2="2.5" width="0.127" layer="51"/>
<rectangle x1="1.5" y1="-0.5" x2="2.75" y2="0.5" layer="51"/>
<rectangle x1="1.5" y1="1.1" x2="2.75" y2="2.1" layer="51"/>
<rectangle x1="1.5" y1="-2.1" x2="2.75" y2="-1.1" layer="51"/>
<rectangle x1="-2.75" y1="-0.5" x2="-1.5" y2="0.5" layer="51" rot="R180"/>
<rectangle x1="-2.75" y1="-2.1" x2="-1.5" y2="-1.1" layer="51" rot="R180"/>
<rectangle x1="-2.75" y1="1.1" x2="-1.5" y2="2.1" layer="51" rot="R180"/>
<smd name="1" x="-2.5" y="1.6" dx="2.1844" dy="1.0668" layer="1" roundness="30"/>
<smd name="2" x="-2.5" y="0" dx="2.1844" dy="1.0668" layer="1" roundness="30"/>
<smd name="3" x="-2.5" y="-1.6" dx="2.1844" dy="1.0668" layer="1" roundness="30"/>
<smd name="4" x="2.5" y="-1.6" dx="2.1844" dy="1.0668" layer="1" roundness="30" rot="R180"/>
<smd name="5" x="2.5" y="0" dx="2.1844" dy="1.0668" layer="1" roundness="30" rot="R180"/>
<smd name="6" x="2.5" y="1.6" dx="2.1844" dy="1.0668" layer="1" roundness="30" rot="R180"/>
<circle x="-3.762" y="2.8" radius="0.2" width="0.4" layer="21"/>
<text x="0" y="2.9" size="1.27" layer="25" font="vector" ratio="15" align="bottom-center">&gt;NAME</text>
<text x="0" y="0" size="0.6096" layer="27" font="vector" ratio="15" rot="R90" align="center">&gt;VALUE</text>
</package>
</packages>
<symbols>
<symbol name="LED_RGB_INDIV">
<description>RGB LED Individual</description>
<wire x1="-5.08" y1="5.08" x2="-5.08" y2="-5.08" width="0.1524" layer="94"/>
<wire x1="5.08" y1="5.08" x2="5.08" y2="-5.08" width="0.1524" layer="94"/>
<wire x1="0" y1="5.08" x2="0" y2="-5.08" width="0.1524" layer="94"/>
<wire x1="-7.62" y1="4.064" x2="-7.62" y2="-4.064" width="0.4064" layer="94" style="shortdash"/>
<wire x1="-7.62" y1="-4.064" x2="7.62" y2="-4.064" width="0.4064" layer="94" style="shortdash"/>
<wire x1="7.62" y1="-4.064" x2="7.62" y2="4.064" width="0.4064" layer="94" style="shortdash"/>
<wire x1="7.62" y1="4.064" x2="-7.62" y2="4.064" width="0.4064" layer="94" style="shortdash"/>
<circle x="-5.08" y="0" radius="1.778" width="0.254" layer="94"/>
<circle x="5.08" y="0" radius="1.778" width="0.254" layer="94"/>
<circle x="0" y="0" radius="1.778" width="0.254" layer="94"/>
<text x="6.35" y="4.572" size="1.016" layer="96">&gt;VALUE</text>
<text x="6.35" y="5.842" size="2.54" layer="95" ratio="15">&gt;NAME</text>
<text x="-1.524" y="-3.556" size="1.4224" layer="94" font="vector">R</text>
<text x="-6.604" y="-3.556" size="1.4224" layer="94" font="vector">G</text>
<text x="3.556" y="-3.556" size="1.4224" layer="94" font="vector">B</text>
<rectangle x1="-6.35" y1="-1.016" x2="-3.81" y2="-0.5842" layer="94"/>
<rectangle x1="3.81" y1="-1.016" x2="6.35" y2="-0.5842" layer="94"/>
<rectangle x1="-1.27" y1="-1.016" x2="1.27" y2="-0.5842" layer="94"/>
<pin name="AG" x="-5.08" y="7.62" visible="pad" length="short" rot="R270"/>
<pin name="KG" x="-5.08" y="-7.62" visible="pad" length="short" rot="R90"/>
<pin name="KR" x="0" y="-7.62" visible="pad" length="short" rot="R90"/>
<pin name="KB" x="5.08" y="-7.62" visible="pad" length="short" rot="R90"/>
<polygon width="0.254" layer="94">
<vertex x="-6.096" y="1.016"/>
<vertex x="-4.064" y="1.016"/>
<vertex x="-5.08" y="-0.508"/>
</polygon>
<polygon width="0.254" layer="94">
<vertex x="4.064" y="1.016"/>
<vertex x="6.096" y="1.016"/>
<vertex x="5.08" y="-0.508"/>
</polygon>
<polygon width="0.254" layer="94">
<vertex x="-1.016" y="1.016"/>
<vertex x="1.016" y="1.016"/>
<vertex x="0" y="-0.508"/>
</polygon>
<pin name="AR" x="0" y="7.62" visible="pad" length="short" rot="R270"/>
<pin name="AB" x="5.08" y="7.62" visible="pad" length="short" rot="R270"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="RGB_INDIV" prefix="LED" uservalue="yes">
<description>RGB LED - SIngle Package - Individual LEDs</description>
<gates>
<gate name="G$1" symbol="LED_RGB_INDIV" x="0" y="2.54"/>
</gates>
<devices>
<device name="OSRAM_LRTD" package="OSRAM_LRTD">
<connects>
<connect gate="G$1" pin="AB" pad="AB"/>
<connect gate="G$1" pin="AG" pad="AG"/>
<connect gate="G$1" pin="AR" pad="AR"/>
<connect gate="G$1" pin="KB" pad="KB"/>
<connect gate="G$1" pin="KG" pad="KG"/>
<connect gate="G$1" pin="KR" pad="KR"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="PLCC-6" package="PLCC-6">
<connects>
<connect gate="G$1" pin="AB" pad="1"/>
<connect gate="G$1" pin="AG" pad="3"/>
<connect gate="G$1" pin="AR" pad="2"/>
<connect gate="G$1" pin="KB" pad="6"/>
<connect gate="G$1" pin="KG" pad="4"/>
<connect gate="G$1" pin="KR" pad="5"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="con-headers">
<description>&lt;b&gt;Headers (Circuit Monkey)&lt;/b&gt;&lt;p&gt;
FE = female&lt;br&gt;
MA = male&lt;p&gt;
# contacts - # rows&lt;p&gt;
W = angled&lt;br&gt;
RA = Right Angle&lt;p&gt;
&lt;author&gt;Created by librarian@cadsoft.de&lt;/author&gt;
&lt;author&gt;Fixed by: mark@maehem.com&lt;/author&gt;</description>
<packages>
<package name="MA04-1">
<description>&lt;b&gt;PIN HEADER&lt;/b&gt;</description>
<wire x1="-5.08" y1="1.27" x2="-3.175" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-3.175" y1="1.27" x2="-2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-2.54" y1="-0.635" x2="-3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-2.54" y1="0.635" x2="-1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="1.27" x2="-0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="1.27" x2="0" y2="0.635" width="0.1524" layer="21"/>
<wire x1="0" y1="-0.635" x2="-0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="-1.27" x2="-1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="-1.27" x2="-2.54" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="-5.08" y1="1.27" x2="-5.08" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-3.175" y1="-1.27" x2="-5.08" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="0" y1="0.635" x2="0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="1.27" x2="1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="1.27" x2="2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="2.54" y1="-0.635" x2="1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="-1.27" x2="0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="-1.27" x2="0" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="3.175" y1="1.27" x2="4.445" y2="1.27" width="0.1524" layer="21"/>
<wire x1="4.445" y1="1.27" x2="5.08" y2="0.635" width="0.1524" layer="21"/>
<wire x1="5.08" y1="0.635" x2="5.08" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="5.08" y1="-0.635" x2="4.445" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="3.175" y1="1.27" x2="2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="2.54" y1="-0.635" x2="3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="4.445" y1="-1.27" x2="3.175" y2="-1.27" width="0.1524" layer="21"/>
<pad name="1" x="-3.81" y="0" drill="0.9652" shape="square" rot="R90"/>
<pad name="2" x="-1.27" y="0" drill="0.9652" rot="R90"/>
<pad name="3" x="1.27" y="0" drill="0.9652" rot="R90"/>
<pad name="4" x="3.81" y="0" drill="0.9652" rot="R90"/>
<text x="-5.969" y="-1.524" size="0.8128" layer="25" ratio="15" rot="R90">&gt;NAME</text>
<text x="-4.445" y="-2.159" size="0.6096" layer="27" ratio="10">&gt;VALUE</text>
<rectangle x1="-1.524" y1="-0.254" x2="-1.016" y2="0.254" layer="51"/>
<rectangle x1="-4.064" y1="-0.254" x2="-3.556" y2="0.254" layer="51"/>
<rectangle x1="1.016" y1="-0.254" x2="1.524" y2="0.254" layer="51"/>
<rectangle x1="3.556" y1="-0.254" x2="4.064" y2="0.254" layer="51"/>
<polygon width="0.1524" layer="21">
<vertex x="-5.08" y="1.27"/>
<vertex x="-5.842" y="1.27"/>
<vertex x="-5.842" y="-1.27"/>
<vertex x="-5.08" y="-1.27"/>
</polygon>
</package>
<package name="MA04-1-EDGE-2.54MM">
<wire x1="-2.54" y1="5.08" x2="2.54" y2="5.08" width="0.127" layer="21"/>
<wire x1="2.54" y1="5.08" x2="2.54" y2="-5.08" width="0.127" layer="21"/>
<wire x1="2.54" y1="-5.08" x2="-2.54" y2="-5.08" width="0.127" layer="21"/>
<wire x1="-2.54" y1="-5.08" x2="-2.54" y2="5.08" width="0.127" layer="21"/>
<smd name="1" x="0" y="3.81" dx="3.81" dy="1.9304" layer="1" roundness="15"/>
<smd name="2" x="0" y="1.27" dx="3.81" dy="1.9304" layer="1" roundness="15"/>
<smd name="3" x="0" y="-1.27" dx="3.81" dy="1.9304" layer="1" roundness="15"/>
<smd name="4" x="0" y="-3.81" dx="3.81" dy="1.9304" layer="1" roundness="15"/>
<text x="-2.54" y="5.334" size="1.9304" layer="25" font="vector" ratio="15">&gt;NAME</text>
<text x="-2.794" y="-5.08" size="1.27" layer="27" rot="R90">&gt;VALUE</text>
<rectangle x1="-2.54" y1="2.794" x2="-2.032" y2="5.08" layer="21"/>
<rectangle x1="2.032" y1="2.794" x2="2.54" y2="5.08" layer="21"/>
</package>
<package name="MA04-1-RA">
<description>Header - Single Row - 4 pin - right angle</description>
<wire x1="-5.08" y1="1.27" x2="-3.175" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-3.175" y1="1.27" x2="-2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-2.54" y1="-0.635" x2="-3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-2.54" y1="0.635" x2="-1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="1.27" x2="-0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="1.27" x2="0" y2="0.635" width="0.1524" layer="21"/>
<wire x1="0" y1="-0.635" x2="-0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="-1.27" x2="-1.016" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.016" y1="-1.27" x2="-1.524" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.524" y1="-1.27" x2="-1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="-1.27" x2="-2.54" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="-5.08" y1="1.27" x2="-5.08" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-3.175" y1="-1.27" x2="-3.556" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-3.556" y1="-1.27" x2="-4.064" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-4.064" y1="-1.27" x2="-5.08" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="0" y1="0.635" x2="0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="1.27" x2="1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="1.27" x2="2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="2.54" y1="-0.635" x2="1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="-1.27" x2="1.524" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.524" y1="-1.27" x2="1.016" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.016" y1="-1.27" x2="0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="-1.27" x2="0" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="3.175" y1="1.27" x2="4.445" y2="1.27" width="0.1524" layer="21"/>
<wire x1="4.445" y1="1.27" x2="5.08" y2="0.635" width="0.1524" layer="21"/>
<wire x1="5.08" y1="0.635" x2="5.08" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="5.08" y1="-0.635" x2="4.445" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="3.175" y1="1.27" x2="2.54" y2="0.635" width="0.1524" layer="21"/>
<wire x1="2.54" y1="-0.635" x2="3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="4.445" y1="-1.27" x2="4.064" y2="-1.27" width="0.1524" layer="21"/>
<pad name="1" x="-3.81" y="0" drill="0.9652" shape="square" rot="R90"/>
<pad name="2" x="-1.27" y="0" drill="0.9652" rot="R90"/>
<pad name="3" x="1.27" y="0" drill="0.9652" rot="R90"/>
<pad name="4" x="3.81" y="0" drill="0.9652" rot="R90"/>
<text x="-5.969" y="0" size="1.27" layer="25" font="vector" ratio="15" rot="R90" align="bottom-center">&gt;NAME</text>
<text x="-2.667" y="1.651" size="0.6096" layer="27" ratio="10">&gt;VALUE</text>
<rectangle x1="-1.524" y1="-0.254" x2="-1.016" y2="0.254" layer="51"/>
<rectangle x1="-4.064" y1="-0.254" x2="-3.556" y2="0.254" layer="51"/>
<rectangle x1="1.016" y1="-0.254" x2="1.524" y2="0.254" layer="51"/>
<rectangle x1="3.556" y1="-0.254" x2="4.064" y2="0.254" layer="51"/>
<wire x1="4.064" y1="-1.27" x2="3.556" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="3.556" y1="-1.27" x2="3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-4.064" y1="-1.27" x2="-4.064" y2="-6.604" width="0.127" layer="21"/>
<wire x1="-4.064" y1="-6.604" x2="-3.556" y2="-6.604" width="0.127" layer="21"/>
<wire x1="-3.556" y1="-6.604" x2="-3.556" y2="-1.27" width="0.127" layer="21"/>
<wire x1="-1.016" y1="-1.27" x2="-1.016" y2="-6.604" width="0.127" layer="21"/>
<wire x1="-1.016" y1="-6.604" x2="-1.524" y2="-6.604" width="0.127" layer="21"/>
<wire x1="-1.524" y1="-6.604" x2="-1.524" y2="-1.27" width="0.127" layer="21"/>
<wire x1="1.016" y1="-1.27" x2="1.016" y2="-6.604" width="0.127" layer="21"/>
<wire x1="1.016" y1="-6.604" x2="1.524" y2="-6.604" width="0.127" layer="21"/>
<wire x1="1.524" y1="-6.604" x2="1.524" y2="-1.27" width="0.127" layer="21"/>
<wire x1="3.556" y1="-1.27" x2="3.556" y2="-6.604" width="0.127" layer="21"/>
<wire x1="3.556" y1="-6.604" x2="4.064" y2="-6.604" width="0.127" layer="21"/>
<wire x1="4.064" y1="-6.604" x2="4.064" y2="-1.27" width="0.127" layer="21"/>
<polygon width="0.1524" layer="21">
<vertex x="-5.08" y="-1.27"/>
<vertex x="-5.588" y="-1.27"/>
<vertex x="-5.588" y="1.778"/>
<vertex x="-3.048" y="1.778"/>
<vertex x="-3.048" y="1.27"/>
<vertex x="-5.08" y="1.27"/>
</polygon>
</package>
</packages>
<symbols>
<symbol name="MA04-1">
<wire x1="3.81" y1="-7.62" x2="-1.27" y2="-7.62" width="0.4064" layer="94"/>
<wire x1="1.27" y1="0" x2="2.54" y2="0" width="0.6096" layer="94"/>
<wire x1="1.27" y1="-2.54" x2="2.54" y2="-2.54" width="0.6096" layer="94"/>
<wire x1="1.27" y1="-5.08" x2="2.54" y2="-5.08" width="0.6096" layer="94"/>
<wire x1="-1.27" y1="5.08" x2="-1.27" y2="-7.62" width="0.4064" layer="94"/>
<wire x1="3.81" y1="-7.62" x2="3.81" y2="5.08" width="0.4064" layer="94"/>
<wire x1="-1.27" y1="5.08" x2="3.81" y2="5.08" width="0.4064" layer="94"/>
<wire x1="1.27" y1="2.54" x2="2.54" y2="2.54" width="0.6096" layer="94"/>
<text x="-1.27" y="-10.16" size="1.778" layer="96">&gt;VALUE</text>
<text x="-1.27" y="5.842" size="1.778" layer="95">&gt;NAME</text>
<pin name="1" x="7.62" y="-5.08" visible="pad" length="middle" direction="pas" swaplevel="1" rot="R180"/>
<pin name="2" x="7.62" y="-2.54" visible="pad" length="middle" direction="pas" swaplevel="1" rot="R180"/>
<pin name="3" x="7.62" y="0" visible="pad" length="middle" direction="pas" swaplevel="1" rot="R180"/>
<pin name="4" x="7.62" y="2.54" visible="pad" length="middle" direction="pas" swaplevel="1" rot="R180"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="MA04-1" prefix="J" uservalue="yes">
<description>&lt;b&gt;PIN HEADER&lt;/b&gt;</description>
<gates>
<gate name="1" symbol="MA04-1" x="0" y="0"/>
</gates>
<devices>
<device name="" package="MA04-1">
<connects>
<connect gate="1" pin="1" pad="1"/>
<connect gate="1" pin="2" pad="2"/>
<connect gate="1" pin="3" pad="3"/>
<connect gate="1" pin="4" pad="4"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="-EDGE-2.54MM" package="MA04-1-EDGE-2.54MM">
<connects>
<connect gate="1" pin="1" pad="1"/>
<connect gate="1" pin="2" pad="2"/>
<connect gate="1" pin="3" pad="3"/>
<connect gate="1" pin="4" pad="4"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="-RA" package="MA04-1-RA">
<connects>
<connect gate="1" pin="1" pad="1"/>
<connect gate="1" pin="2" pad="2"/>
<connect gate="1" pin="3" pad="3"/>
<connect gate="1" pin="4" pad="4"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="rlc">
<description>Custom RCL Library - Resistors, Capacitors, Inductors</description>
<packages>
<package name="R_THD_204/7">
<wire x1="3.81" y1="0" x2="2.921" y2="0" width="0.508" layer="51"/>
<wire x1="-3.81" y1="0" x2="-2.921" y2="0" width="0.508" layer="51"/>
<wire x1="-2.54" y1="0.762" x2="-2.286" y2="1.016" width="0.1524" layer="21" curve="-90"/>
<wire x1="-2.54" y1="-0.762" x2="-2.286" y2="-1.016" width="0.1524" layer="21" curve="90"/>
<wire x1="2.286" y1="-1.016" x2="2.54" y2="-0.762" width="0.1524" layer="21" curve="90"/>
<wire x1="2.286" y1="1.016" x2="2.54" y2="0.762" width="0.1524" layer="21" curve="-90"/>
<wire x1="-2.54" y1="-0.762" x2="-2.54" y2="0.762" width="0.1524" layer="21"/>
<wire x1="-2.286" y1="1.016" x2="-1.905" y2="1.016" width="0.1524" layer="21"/>
<wire x1="-1.778" y1="0.889" x2="-1.905" y2="1.016" width="0.1524" layer="21"/>
<wire x1="-2.286" y1="-1.016" x2="-1.905" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="-1.778" y1="-0.889" x2="-1.905" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="1.778" y1="0.889" x2="1.905" y2="1.016" width="0.1524" layer="21"/>
<wire x1="1.778" y1="0.889" x2="-1.778" y2="0.889" width="0.1524" layer="21"/>
<wire x1="1.778" y1="-0.889" x2="1.905" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="1.778" y1="-0.889" x2="-1.778" y2="-0.889" width="0.1524" layer="21"/>
<wire x1="2.286" y1="1.016" x2="1.905" y2="1.016" width="0.1524" layer="21"/>
<wire x1="2.286" y1="-1.016" x2="1.905" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="2.54" y1="-0.762" x2="2.54" y2="0.762" width="0.1524" layer="21"/>
<pad name="1" x="-3.81" y="0" drill="0.889"/>
<pad name="2" x="3.81" y="0" drill="0.889"/>
<text x="-2.54" y="1.2954" size="0.9906" layer="25" ratio="12">&gt;NAME</text>
<text x="-1.6256" y="-0.4826" size="0.9906" layer="27" ratio="12">&gt;VALUE</text>
<rectangle x1="2.54" y1="-0.254" x2="2.921" y2="0.254" layer="21"/>
<rectangle x1="-2.921" y1="-0.254" x2="-2.54" y2="0.254" layer="21"/>
</package>
<package name="1206">
<description>SMT 1206 Package</description>
<wire x1="-2.423" y1="1.033" x2="2.423" y2="1.033" width="0.0508" layer="39"/>
<wire x1="2.423" y1="1.033" x2="2.423" y2="-1.033" width="0.0508" layer="39"/>
<wire x1="2.423" y1="-1.033" x2="-2.423" y2="-1.033" width="0.0508" layer="39"/>
<wire x1="-2.423" y1="-1.033" x2="-2.423" y2="1.033" width="0.0508" layer="39"/>
<wire x1="2.413" y1="1.093" x2="2.413" y2="-1.093" width="0.127" layer="21"/>
<wire x1="2.413" y1="-1.093" x2="-2.413" y2="-1.093" width="0.127" layer="21"/>
<wire x1="-2.413" y1="-1.093" x2="-2.413" y2="1.093" width="0.127" layer="21"/>
<wire x1="-2.413" y1="1.093" x2="2.413" y2="1.093" width="0.127" layer="21"/>
<wire x1="-1.6" y1="0.8" x2="1.6" y2="0.8" width="0.0508" layer="51"/>
<wire x1="1.6" y1="0.8" x2="1.6" y2="-0.8" width="0.0508" layer="51"/>
<wire x1="1.6" y1="-0.8" x2="-1.6" y2="-0.8" width="0.0508" layer="51"/>
<wire x1="-1.6" y1="-0.8" x2="-1.6" y2="0.8" width="0.0508" layer="51"/>
<smd name="2" x="1.472" y="0" dx="1.5" dy="1.803" layer="1" roundness="20"/>
<smd name="1" x="-1.472" y="0" dx="1.5" dy="1.803" layer="1" roundness="20"/>
<text x="-1.82" y="1.27" size="1.27" layer="25" ratio="15">&gt;NAME</text>
<text x="-2.032" y="-2.059" size="0.8128" layer="27">&gt;VALUE</text>
<rectangle x1="-0.3" y1="-0.7" x2="0.3" y2="0.7" layer="35"/>
<rectangle x1="-1.6" y1="-0.8" x2="-0.8" y2="0.8" layer="51"/>
<rectangle x1="0.8" y1="-0.8" x2="1.6" y2="0.8" layer="51"/>
</package>
<package name="0805">
<description>SMT 0805 Package</description>
<wire x1="-1.846" y1="0.983" x2="1.846" y2="0.983" width="0.0508" layer="39"/>
<wire x1="1.846" y1="0.983" x2="1.846" y2="-0.983" width="0.0508" layer="39"/>
<wire x1="1.846" y1="-0.983" x2="-1.846" y2="-0.983" width="0.0508" layer="39"/>
<wire x1="-1.846" y1="-0.983" x2="-1.846" y2="0.983" width="0.0508" layer="39"/>
<wire x1="-1.905" y1="0.966" x2="1.905" y2="0.966" width="0.127" layer="21"/>
<wire x1="1.905" y1="0.966" x2="1.905" y2="-0.966" width="0.127" layer="21"/>
<wire x1="1.905" y1="-0.966" x2="-1.905" y2="-0.966" width="0.127" layer="21"/>
<wire x1="-1.905" y1="-0.966" x2="-1.905" y2="0.966" width="0.127" layer="21"/>
<wire x1="-1.016" y1="0.635" x2="1.016" y2="0.635" width="0.0508" layer="51"/>
<wire x1="1.016" y1="0.635" x2="1.016" y2="-0.635" width="0.0508" layer="51"/>
<wire x1="1.016" y1="-0.635" x2="-1.016" y2="-0.635" width="0.0508" layer="51"/>
<wire x1="-1.016" y1="-0.635" x2="-1.016" y2="0.635" width="0.0508" layer="51"/>
<smd name="1" x="-1.1" y="0" dx="1.2" dy="1.5" layer="1" roundness="20"/>
<smd name="2" x="1.1" y="0" dx="1.2" dy="1.5" layer="1" roundness="20"/>
<text x="-1.397" y="1.27" size="1.27" layer="25" font="vector" ratio="15">&gt;NAME</text>
<text x="-1.524" y="-1.778" size="0.6096" layer="27" font="vector">&gt;VALUE</text>
<rectangle x1="-0.1999" y1="-0.5001" x2="0.1999" y2="0.5001" layer="35"/>
<rectangle x1="-1.016" y1="-0.635" x2="-0.6096" y2="0.6223" layer="51"/>
<rectangle x1="0.6096" y1="-0.6223" x2="1.016" y2="0.6349" layer="51" rot="R180"/>
</package>
<package name="0603">
<description>0603 Non-polarized</description>
<wire x1="-1.35" y1="0.65" x2="-0.4" y2="0.65" width="0.127" layer="21"/>
<wire x1="0.4" y1="0.65" x2="1.35" y2="0.65" width="0.127" layer="21"/>
<wire x1="1.35" y1="0.65" x2="1.35" y2="-0.65" width="0.127" layer="21"/>
<wire x1="1.35" y1="-0.65" x2="0.4" y2="-0.65" width="0.127" layer="21"/>
<wire x1="-0.4" y1="-0.65" x2="-1.35" y2="-0.65" width="0.127" layer="21"/>
<wire x1="-1.35" y1="-0.65" x2="-1.35" y2="0.65" width="0.127" layer="21"/>
<wire x1="-0.8" y1="0.4" x2="0.8" y2="0.4" width="0.0508" layer="51"/>
<wire x1="0.8" y1="0.4" x2="0.8" y2="-0.4" width="0.0508" layer="51"/>
<wire x1="0.8" y1="-0.4" x2="-0.8" y2="-0.4" width="0.0508" layer="51"/>
<wire x1="-0.8" y1="-0.4" x2="-0.8" y2="0.4" width="0.0508" layer="51"/>
<smd name="1" x="-0.763" y="0" dx="0.8" dy="0.9" layer="1" roundness="20"/>
<smd name="2" x="0.763" y="0" dx="0.8" dy="0.9" layer="1" roundness="20"/>
<text x="-1.5" y="0.9" size="0.8128" layer="25" ratio="12">&gt;NAME</text>
<text x="-1.2" y="-1.15" size="0.4064" layer="27">&gt;VALUE</text>
<rectangle x1="-0.8" y1="-0.4" x2="-0.4" y2="0.4" layer="51"/>
<rectangle x1="0.4" y1="-0.4" x2="0.8" y2="0.4" layer="51"/>
</package>
<package name="R_THD_610/236">
<description>Resistor, THD, 2 Watt</description>
<wire x1="-5.5" y1="3.5" x2="-5" y2="3" width="0.3048" layer="21"/>
<wire x1="-5" y1="3" x2="5" y2="3" width="0.3048" layer="21"/>
<wire x1="5" y1="3" x2="5.5" y2="3.5" width="0.3048" layer="21"/>
<wire x1="5.5" y1="3.5" x2="7.5" y2="3.5" width="0.3048" layer="21"/>
<wire x1="7.5" y1="3.5" x2="8" y2="3" width="0.3048" layer="21"/>
<wire x1="8" y1="3" x2="8" y2="-3" width="0.3048" layer="21"/>
<wire x1="8" y1="-3" x2="7.5" y2="-3.5" width="0.3048" layer="21"/>
<wire x1="7.5" y1="-3.5" x2="5.5" y2="-3.5" width="0.3048" layer="21"/>
<wire x1="5.5" y1="-3.5" x2="5" y2="-3" width="0.3048" layer="21"/>
<wire x1="5" y1="-3" x2="-5" y2="-3" width="0.3048" layer="21"/>
<wire x1="-5" y1="-3" x2="-5.5" y2="-3.5" width="0.3048" layer="21"/>
<wire x1="-5.5" y1="-3.5" x2="-7.5" y2="-3.5" width="0.3048" layer="21"/>
<wire x1="-7.5" y1="-3.5" x2="-8" y2="-3" width="0.3048" layer="21"/>
<wire x1="-8" y1="-3" x2="-8" y2="3" width="0.3048" layer="21"/>
<wire x1="-8" y1="3" x2="-7.5" y2="3.5" width="0.3048" layer="21"/>
<wire x1="-7.5" y1="3.5" x2="-5.5" y2="3.5" width="0.3048" layer="21"/>
<wire x1="-8.7" y1="0" x2="-8.3" y2="0" width="0.8128" layer="21"/>
<wire x1="8.3" y1="0" x2="8.7" y2="0" width="0.8128" layer="21"/>
<pad name="P$1" x="-10" y="0" drill="1"/>
<pad name="P$2" x="10" y="0" drill="1"/>
<text x="-7.6" y="4" size="1.27" layer="25" ratio="15">&gt;NAME</text>
<text x="-6.5" y="-0.2" size="0.6096" layer="27">&gt;VALUE</text>
</package>
<package name="2512">
<description>SMD Package 2512</description>
<wire x1="-3.1496" y1="1.5494" x2="3.1496" y2="1.5494" width="0.127" layer="51"/>
<wire x1="3.1496" y1="1.5494" x2="3.1496" y2="-1.5494" width="0.127" layer="51"/>
<wire x1="3.1496" y1="-1.5494" x2="-3.1496" y2="-1.5494" width="0.127" layer="51"/>
<wire x1="-3.1496" y1="-1.5494" x2="-3.1496" y2="1.5494" width="0.127" layer="51"/>
<wire x1="-4.572" y1="2.032" x2="4.572" y2="2.032" width="0.127" layer="21"/>
<wire x1="4.572" y1="2.032" x2="4.572" y2="-2.032" width="0.127" layer="21"/>
<wire x1="4.572" y1="-2.032" x2="-4.572" y2="-2.032" width="0.127" layer="21"/>
<wire x1="-4.572" y1="-2.032" x2="-4.572" y2="2.032" width="0.127" layer="21"/>
<smd name="1" x="-3.302" y="0" dx="2.032" dy="3.556" layer="1"/>
<smd name="2" x="3.302" y="0" dx="2.032" dy="3.556" layer="1" rot="R180"/>
<text x="-4.318" y="2.286" size="0.8128" layer="25" ratio="15">&gt;NAME</text>
<text x="-2.032" y="-0.508" size="0.4064" layer="27" font="vector">&gt;VALUE</text>
<rectangle x1="2.4892" y1="-1.5494" x2="3.1496" y2="1.5494" layer="51"/>
<rectangle x1="-3.1496" y1="-1.5494" x2="-2.4892" y2="1.5494" layer="51" rot="R180"/>
</package>
</packages>
<symbols>
<symbol name="RES-EU">
<description>RESISTOR</description>
<wire x1="-2.54" y1="-0.889" x2="2.54" y2="-0.889" width="0.4064" layer="94"/>
<wire x1="2.54" y1="0.889" x2="-2.54" y2="0.889" width="0.4064" layer="94"/>
<wire x1="2.54" y1="-0.889" x2="2.54" y2="0.889" width="0.4064" layer="94"/>
<wire x1="-2.54" y1="-0.889" x2="-2.54" y2="0.889" width="0.4064" layer="94"/>
<text x="0" y="1.4986" size="1.778" layer="95" font="vector" ratio="15" align="bottom-center">&gt;NAME</text>
<text x="0" y="0" size="1.143" layer="96" font="vector" align="center">&gt;VALUE</text>
<pin name="2" x="5.08" y="0" visible="off" length="short" direction="pas" swaplevel="1" rot="R180"/>
<pin name="1" x="-5.08" y="0" visible="off" length="short" direction="pas" swaplevel="1"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="RESISTOR" prefix="R" uservalue="yes">
<description>Resistor</description>
<gates>
<gate name="G$1" symbol="RES-EU" x="0" y="0"/>
</gates>
<devices>
<device name="THD_204/7" package="R_THD_204/7">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="2" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="1206" package="1206">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="2" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="0805" package="0805">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="2" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="0603" package="0603">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="2" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="THD_610/236" package="R_THD_610/236">
<connects>
<connect gate="G$1" pin="1" pad="P$1"/>
<connect gate="G$1" pin="2" pad="P$2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="2512" package="2512">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="2" pad="2"/>
</connects>
<technologies>
<technology name=""/>
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
<part name="U$1" library="frames-custom" deviceset="LETTER_L-LIGHTPLAY" device=""/>
<part name="LED" library="led" deviceset="RGB_INDIV" device="PLCC-6"/>
<part name="J1" library="con-headers" deviceset="MA04-1" device=""/>
<part name="R2" library="rlc" deviceset="RESISTOR" device="0805" value="200"/>
<part name="R3" library="rlc" deviceset="RESISTOR" device="0805" value="133"/>
<part name="R1" library="rlc" deviceset="RESISTOR" device="0805" value="133"/>
</parts>
<sheets>
<sheet>
<plain>
<text x="134.62" y="106.68" size="1.778" layer="97">Stuff LED 180 degrees for 
Rev. A-D Brain boards 
(common cathode).</text>
</plain>
<instances>
<instance part="U$1" gate="G$1" x="0" y="0"/>
<instance part="U$1" gate="G$2" x="76.2" y="0"/>
<instance part="LED" gate="G$1" x="121.92" y="101.6" rot="MR180"/>
<instance part="J1" gate="1" x="86.36" y="114.3" rot="MR180"/>
<instance part="R2" gate="G$1" x="106.68" y="114.3"/>
<instance part="R3" gate="G$1" x="106.68" y="119.38"/>
<instance part="R1" gate="G$1" x="106.68" y="109.22"/>
</instances>
<busses>
</busses>
<nets>
<net name="N$3" class="0">
<segment>
<pinref part="R3" gate="G$1" pin="2"/>
<wire x1="111.76" y1="119.38" x2="127" y2="119.38" width="0.1524" layer="91"/>
<pinref part="LED" gate="G$1" pin="KB"/>
<wire x1="127" y1="119.38" x2="127" y2="109.22" width="0.1524" layer="91"/>
</segment>
</net>
<net name="N$6" class="0">
<segment>
<pinref part="J1" gate="1" pin="1"/>
<pinref part="R3" gate="G$1" pin="1"/>
<wire x1="93.98" y1="119.38" x2="101.6" y2="119.38" width="0.1524" layer="91"/>
</segment>
</net>
<net name="N$5" class="0">
<segment>
<pinref part="J1" gate="1" pin="2"/>
<wire x1="93.98" y1="116.84" x2="99.06" y2="116.84" width="0.1524" layer="91"/>
<wire x1="99.06" y1="116.84" x2="99.06" y2="109.22" width="0.1524" layer="91"/>
<pinref part="R1" gate="G$1" pin="1"/>
<wire x1="99.06" y1="109.22" x2="101.6" y2="109.22" width="0.1524" layer="91"/>
</segment>
</net>
<net name="COMMON" class="0">
<segment>
<pinref part="J1" gate="1" pin="4"/>
<wire x1="93.98" y1="111.76" x2="96.52" y2="111.76" width="0.1524" layer="91"/>
<wire x1="96.52" y1="111.76" x2="96.52" y2="93.98" width="0.1524" layer="91"/>
<label x="99.06" y="93.98" size="1.778" layer="95"/>
<pinref part="LED" gate="G$1" pin="AB"/>
<pinref part="LED" gate="G$1" pin="AR"/>
<wire x1="127" y1="93.98" x2="121.92" y2="93.98" width="0.1524" layer="91"/>
<pinref part="LED" gate="G$1" pin="AG"/>
<wire x1="121.92" y1="93.98" x2="116.84" y2="93.98" width="0.1524" layer="91"/>
<wire x1="116.84" y1="93.98" x2="96.52" y2="93.98" width="0.1524" layer="91"/>
<junction x="116.84" y="93.98"/>
<junction x="121.92" y="93.98"/>
</segment>
</net>
<net name="N$1" class="0">
<segment>
<pinref part="R1" gate="G$1" pin="2"/>
<pinref part="LED" gate="G$1" pin="KG"/>
<wire x1="111.76" y1="109.22" x2="116.84" y2="109.22" width="0.1524" layer="91"/>
</segment>
</net>
<net name="N$2" class="0">
<segment>
<pinref part="J1" gate="1" pin="3"/>
<pinref part="R2" gate="G$1" pin="1"/>
<wire x1="93.98" y1="114.3" x2="101.6" y2="114.3" width="0.1524" layer="91"/>
</segment>
</net>
<net name="N$4" class="0">
<segment>
<pinref part="R2" gate="G$1" pin="2"/>
<pinref part="LED" gate="G$1" pin="KR"/>
<wire x1="111.76" y1="114.3" x2="121.92" y2="114.3" width="0.1524" layer="91"/>
<wire x1="121.92" y1="114.3" x2="121.92" y2="109.22" width="0.1524" layer="91"/>
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
