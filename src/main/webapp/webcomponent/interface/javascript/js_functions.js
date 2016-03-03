// browser version

var agt=navigator.userAgent.toLowerCase();
var isNav  = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1)
              && (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1)
              && (agt.indexOf('webtv')==-1));
var isIe   = (agt.indexOf("msie") != -1);

       // validates the form for submission
       // the only thing that will be validated on Server
       // side is the team name duplication
       function validateRegistrationForm(budget, maxPoints)
       {
          var objTeamManager = document.registrationForm.teamManager.value;
          var objTeamName = document.registrationForm.teamName.value;
          var objEmailAddress = document.registrationForm.emailAddress.value;
          var objTieBreakerQuestion = document.registrationForm.tbQuestion.value;
          
          // text fields validation
          if (objTeamManager.length == 0 || objTeamName.length == 0 || objEmailAddress.lenght == 0)
          {
             alert('Please fill in all the mandatory fields!');
             return false;
          }
          
          // email address validation
          if ((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(objEmailAddress)) == false)
          {
             alert("E-mail Address is missing or invalid! Please (re-)enter.")
             return false;
          }
          
          // tiebreaker result validation (must be a number between 0 and maxPoints)
          if ( objTieBreakerQuestion.length == 0 ||
               isNaN(Number(objTieBreakerQuestion)) ||
               Number(objTieBreakerQuestion) < 0 ||
               Number(objTieBreakerQuestion) > maxPoints )
          {
             alert("Tie-Breaker response is missing or invalid!\n\nPlease enter a number between 0 and " + maxPoints + ".");
             return false;
          }
          
          // component selection validation
          
          // get the selected drivers
          var allDrivers = [];
          var selectedDrivers = new Array();
          while (true)
          {
             var id = "drivercheckbox_" + allDrivers.length;
             var checkbox = window.document.getElementById(id);
             if (checkbox == null)
             {
                break;
             }

             allDrivers[allDrivers.length] = checkbox.checked;
             if (checkbox.checked)
             {
               // get the driverId (it is stored on the checkbox's name)
               // as well as the the driver's cost( stored on the td's 'title' attribute)
               
               var costid = "cost_" + checkbox.id;
               var costTD = window.document.getElementById(costid);
               
               selectedDrivers[selectedDrivers.length] = new Array(2);
               
               // the driver ID
               selectedDrivers[selectedDrivers.length-1][0] = checkbox.name;
               
               // the driver cost
               selectedDrivers[selectedDrivers.length-1][1] = costTD.title;
             }
          }
          
          // get the selected teams
          var allTeams = [];
          var selectedTeams = new Array();
          while (true)
          {
             var id = "teamcheckbox_" + allTeams.length;
             var checkbox = window.document.getElementById(id);
             if (checkbox == null)
             {
                break;
             }

             allTeams[allTeams.length] = checkbox.checked;
             if (checkbox.checked)
             {
               var costid = "cost_" + checkbox.id;
               var costTD = window.document.getElementById(costid);
               
               // get the teamId (it is stored on the checkbox's name)
               // as well as the the team's's cost (stored on the td's 'title' attribute)
               
               selectedTeams[selectedTeams.length] = new Array(2);
               
               // the team ID
               selectedTeams[selectedTeams.length-1][0] = checkbox.name;
               
               // the team cost
               selectedTeams[selectedTeams.length-1][1] = costTD.title;
             }
          }
          
          if (selectedDrivers.length != 2 || selectedTeams.length != 2)
          {
             // no way to go further, there should be 2 drivers and 2 teams!
             alert('Invalid selection! (it should be 2 drivers and 2 teams)');
             return false;
          }
          else
          {
	     // all ok till here, we have 4 selections, 2 drivers and 2 cars
             // now cheeck if we are within the budget
             var selectionCost = parseInt(selectedDrivers[0][1], 10)+
                                 parseInt(selectedDrivers[1][1], 10)+
                                 parseInt(selectedTeams[0][1], 10)+
                                 parseInt(selectedTeams[1][1], 10);
             if (selectionCost > parseInt(budget, 10))
             {
                alert('Budget exceeded! Your current selection costs $'+selectionCost+' million.\n\nPlease stay within your overall budget of $'+budget+' million.');
                return false;
             }
             else
             {
                // everything is perfect, now ask the user to confirm
                if (confirm('Are you sure?\n\nPress OK if you want to submit this selection,\notherwise press Cancel and refine it.'))
                {
                  // prepare the data for submission and return
                  document.registrationForm.driverId1.value = selectedDrivers[0][0];
                  document.registrationForm.driverId2.value = selectedDrivers[1][0];
                  document.registrationForm.teamId1.value = selectedTeams[0][0];
                  document.registrationForm.teamId2.value = selectedTeams[1][0];
                  return true;
                }
                else
                {
                  return false;
                }
             }
          }
       }


      function displayRaceDetail(frame, option)
      {
         if (option.value == 'nohref')
         {
            return false;
         }
         top.mainframe.dataframe.location.href = option.value;
      }

      function displayRaceStandings(frame, option)
      {
         if (option.value == 'nohref')
         {
            return false;
         }
         top.mainframe.dataframe.location.href = option.value;
      }

      function resetRaceOptionSelector()
      {
        window.document.getElementById("statRaceSelectorCtrl").options[0].selected = true;
      }
      