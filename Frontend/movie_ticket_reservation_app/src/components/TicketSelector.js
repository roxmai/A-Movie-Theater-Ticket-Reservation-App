import React, {useState, useEffect} from "react";
import { Box, Divider, Button, ButtonGroup, Card, CardMedia, IconButton, List, ListItem, ListItemButton, ListItemText, Typography } from "@mui/material";
import CheckIcon from '@mui/icons-material/Check';
import { ChevronLeft } from "@mui/icons-material";
import { useHorizontalScroll } from "../utils/horizontalScroll";
function MovieOverview({title, image}) {
    return (
        <Box sx={{display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center'
        }}>
            <Box
            sx={{display: 'flex',
                flexDirection: 'column',
            }}
            >
                <Typography variant="h3">
                    {title}
                </Typography>
                <Card>
                    <Box>
                        <CardMedia 
                            component='img'
                            image={image}
                            sx={{width: 300, height: 380}}
                        />
                    </Box>
                </Card>
            </Box>
        </Box>
    );
}

function MovieDetail({movie, handleBuyTickets}) {
    return (
        <Box
            sx={{display: 'flex', 
                flexDirection: 'column',
                gap: 2,
                padding: 6
            }}
        >
            <Typography variant="h6">
                Summary
            </Typography>
            <Typography variant="body">
                Genre: {movie.genre} Length: {movie.length} min
            </Typography>
            <Typography variant="body">
                Release date: {movie.releaseDate}
            </Typography>
            <Typography variant="body">
                {movie.description}
            </Typography>
            {
                !movie.hasShow && 
                <Typography variant="body" color="error">
                    This movie has no showtime right now.
                </Typography>
            }
            <Button disabled={!movie.hasShow} variant="contained" sx={{width: 200, mt: 4}} onClick={()=>{handleBuyTickets(1)}}>
                Buy tickets
            </Button>
        </Box>
    );
}

function TheatreSelector({theatres, selected, setTheatre, handleBuyTickets, handleBack}) {
    return (
        <Box
        sx={{display: 'flex',
            flexDirection: 'column',
            gap: 2
        }}
        >
            <Box sx={{display: 'flex', gap: 2, alignItems: 'center', justifyContent: 'space-between'}}>
                <Box><IconButton sx={{padding: 0}} onClick={()=>{handleBack(2)}}><ChevronLeft /></IconButton></Box>
                <Box>Select theatre</Box>
                <Box width={24}></Box>
            </Box>
            <Box>
                <List>
                    {
                        theatres.map((theatre)=>
                            <ListItem key={theatre.id} disablePadding
                            >
                                <ListItemButton sx={{padding: 0}} onClick={()=>{setTheatre(theatre.id)}}>
                                <ListItemText 
                                primary={theatre.name} 
                                secondary={theatre.location}
                                />
                                {
                                    selected === theatre.id && <CheckIcon color="success"/>
                                }
                                </ListItemButton>
                            </ListItem>
                        )
                    }
                </List>
            </Box>
            <Box sx={{display: 'flex', justifyContent: 'center'}}>
                <Button disabled={selected===0} variant="contained" onClick={()=>{handleBuyTickets(2);}}>Confirm</Button>
            </Box>
        </Box>
    );
}

function ShowtimeSelector({showtimes, selected, setShowtime, handleBuyTickets, handleBack}) {
    const dates = Object.keys(showtimes);
    const [selectedDate, setSelectedDate] = useState(null);
    const scrollRef = useHorizontalScroll();
    const [showtimeList, setShowtimeList] = useState([]);

    
    useEffect(()=>{
        setSelectedDate(dates[0]);
        setShowtimeList(showtimes[dates[0]])
        console.log(dates);
    }, [])

    return (
        <Box
        sx={{display: 'flex',
            flexDirection: 'column',
            gap: 2
        }}
        >
            <Box sx={{display: 'flex', gap: 2, alignItems: 'center', justifyContent: 'space-between'}}>
                <Box><IconButton sx={{padding: 0}} onClick={()=>{handleBack(3)}}><ChevronLeft /></IconButton></Box>
                <Box>Select showtime</Box>
                <Box width={24}></Box>
            </Box>
            <Box
            ref={scrollRef}
            sx={{
            display: "flex",
            overflowX: "auto",
            whiteSpace: "nowrap",
            '&::-webkit-scrollbar': {
                display: "none",
            },
            }}>
                <ButtonGroup variant="outlined" sx={{flexWrap: "nowrap"}} fullWidth>
                    {
                        dates.map((date, index)=> 
                            <Button key={index}
                            variant={selectedDate===date?"contained":"outlined"}
                            onClick={(event)=>{setShowtimeList(showtimes[date]); setSelectedDate(date)}}
                            >
                                {date}
                            </Button>
                        )
                    }
                </ButtonGroup>
            </Box>
            <Box sx={{
                height: 330,
                overflowY: 'auto',
                scrollbarWidth: 'none'
            }}>
                <List>
                    {showtimeList.map((showtime, index)=>(
                        <React.Fragment key={showtime.id}>
                            <ListItem  key={showtime.id}>
                                <ListItemButton 
                                onClick={()=>{setShowtime(showtime.id)}}
                                disabled = {showtime.state==='Full' || showtime.state==='Closed'}
                                >
                                    <ListItemText 
                                    primary={showtime.startTime + " - " + showtime.endTime}
                                    secondary={(showtime.tickets-showtime.ticketsSold) + " tickts left"}
                                    />
                                    <Typography variant="body2">
                                        {showtime.state}
                                    </Typography>
                                    {
                                        selected === showtime.id && <CheckIcon color="success"/>
                                    }
                                </ListItemButton>
                            </ListItem>
                            {index < showtimeList.length - 1 && <Divider />}
                        </React.Fragment>
                    ))}
                </List>
            </Box>
            <Box sx={{display: 'flex', justifyContent: 'center'}}>
                <Button disabled={selected===0} variant="contained" onClick={()=>{handleBuyTickets(3)}}>Confirm</Button>
            </Box>
        </Box>
    );
}

function SeatArea({theatreRows, theatreColumns, seats, selected, setSelected}) {
    const boxSizeX = 20;
    const boxSizeY = 20;

    useEffect(()=>{
        console.log("SeatArea:", seats)
    })

    const seatStyles = (state) => {
        switch (state) {
          case "available":
            return {
                border: "2px solid darkgrey", // Outlined for available seats
                backgroundColor: "transparent",
                cursor: "pointer",
              };
          case "reserved":
            return {
                border: "2px solid darkgrey",
                backgroundColor: "lightgrey", // Filled for selected seats
              };
          case "selected":
            return { 
                border: "2px solid darkgrey",
                backgroundColor: "lightgreen",
                cursor: "pointer"
            };
          default:
            return {};
        }
      };
      return (
        <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
          {/* Render rows */}
          {Array.from({ length: theatreRows }).map((_, rowIndex) => (
            <Box
              key={rowIndex}
              sx={{
                display: "flex",
                gap: 2,
                justifyContent: "center",
              }}
            >
              {/* Render columns */}
              {Array.from({ length: theatreColumns }).map((_, colIndex) => {
                const seat = seats.find(
                  (s) =>
                    s.theatreRow === rowIndex + 1 &&
                    s.theatreColumn === colIndex + 1
                );
                return (
                    <Box
                      key={seat ? seat.id : `${rowIndex}-${colIndex}`}
                      sx={{
                        width: boxSizeX,
                        height: boxSizeY,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        borderRadius: 1,
                        backgroundColor: seat ? seatStyles(seat.state).backgroundColor : "transparent",
                        border: seat ? seatStyles(seat.state).border : "none",
                        cursor: seat ? seatStyles(seat.state).cursor : "default",
                      }}
                      onClick={() =>{
                        if(seat) {
                            if (seat.state==='available'){
                                seat.state = 'selected'
                                console.log("Selected: ", selected)
                                setSelected([...selected, seat.id]);
                                console.log("Selected: ", selected)
                            } else if(seat.state==='selected') {
                                seat.state = 'available';
                                console.log("Selected: ", selected)
                                setSelected(selected.filter(num => num!==seat.id));   
                            }
                        }
                      }
                      }
                    >
                    </Box>
                  );
              })}
            </Box>
          ))}
        </Box>
      );
}

function SeatSelector({handleBack, handleBuyTickets, theatreRows, theatreColumns, seats, selected, setSelected}) {
    useEffect(()=>{
        console.log(seats);
        console.log(theatreRows, theatreColumns);
    })
    return (
        <Box
        sx={{display: 'flex',
            flexDirection: 'column',
            gap: 2
        }}
        >
            <Box sx={{display: 'flex', gap: 2, alignItems: 'center', justifyContent: 'space-between'}}>
                <Box><IconButton sx={{padding: 0}} onClick={()=>{handleBack(4)}}><ChevronLeft /></IconButton></Box>
                <Box>Select seats</Box>
                <Box width={24}></Box>
            </Box>
            <Box>
                Map
                <SeatArea 
                theatreRows={theatreRows}
                theatreColumns={theatreColumns}
                seats={seats}
                selected={selected}
                setSelected={setSelected}
                />
            </Box>
            <Box sx={{display: 'flex', justifyContent: 'center'}}>
                <Button disabled={selected.length===0} variant="contained">Confirm</Button>
            </Box>
        </Box>
    )
}

function BookingStepper({step, movie, handleBuyTickets, handleBack,
                        theatres, selectedTheatre, setTheatre,
                        showtimes, selectedShowtime, setShowtime,
                        theatreRows, theatreColumns, 
                        seats, selectedSeats, setSelectedSeats}) {
    return (
        <Box sx={{
            width: 400,
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center'
            }}>
            {step===1 && 
            <MovieDetail 
            movie={movie}
            handleBuyTickets={handleBuyTickets}
            />}
            {step===2 && 
            <TheatreSelector 
            setTheatre={setTheatre}
            handleBuyTickets={handleBuyTickets}
            handleBack={handleBack}
            theatres={theatres} 
            selected={selectedTheatre}/>
            }
            {step===3 &&
            <ShowtimeSelector 
            handleBuyTickets={handleBuyTickets}
            setShowtime={setShowtime}
            handleBack={handleBack}
            showtimes={showtimes} 
            selected={selectedShowtime} />
            }
            {step===4 &&
            <SeatSelector 
            handleBack={handleBack}
            theatreRows={theatreRows}
            theatreColumns={theatreColumns}
            seats={seats} 
            selected={selectedSeats}
            setSelected={setSelectedSeats} />
            }
        </Box>
    );
}

function TicketSelector() {
    const [movie, setMovie] = useState(null);
    const [theatre, setTheatre] = useState(null);
    const [showtime, setShowtime] = useState(null);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [step, setStep] = useState(1);
    const [steps, setSteps] = useState([1, 2, 3, 4]);
    const [theatres, setTheatres] = useState([]);
    const [showtimes, setShowtimes] = useState([]);
    const [seats, setSeats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [theatreRows, setTheatreRows]  = useState(0);
    const [theatreColumns, setTheatreColumns] = useState(0);

    const handleBuyTickets = (currentStep) => {
        setStep(currentStep+1);
        console.log(step);
    }

    const handleBack = (currentStep) => {
        setStep(currentStep-1);
    }

    const handleSetTheatre = (id) => {
        const theTheatre = theatres.filter((theatre)=>theatre.id===id).pop();
        setTheatre(theTheatre);
        setTheatreRows(theTheatre.rows);
        setTheatreColumns(theTheatre.columns);
    }

    const handleSetShowtime = (id) => {
        const allShowtimes = Object.values(showtimes).flat();  
        setShowtime(allShowtimes.find((showtime) => showtime.id === id));
    }

    useEffect(()=>{
        setMovie({
            id: 18, 
            title: "Red One", 
            image: "http://localhost:8080/images/posters/RedOnePoster.jpg",
            releaseDate: "2024-11-08",
            description: "A movie dsafds fasdgagsdg dsag dgasdgsadgasdgasdgasdga ggasgg adgasg gdasgasgsdg adh ahsdhah ahadha adgahh ahrrha ahrheh gadg gas.",
            length: 121,
            genre: "Comedy",
            hasShow: true
        });
        setTheatres([
            {id: 1, name: 'AcmePlex movie theatre', location: 'Calgary'},
            {id: 2, name: 'AcmePlex royal', location: 'Edmonton'}
        ]);
        setStep(1);
        setShowtimes({
            "11-17": [
                {id: 1, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 40, state: "Full"},
                {id: 2, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 38, state: "Closed"},
                {id: 4, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"},
                {id: 5, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 40, state: "Full"},
                {id: 6, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 38, state: "open"},
                {id: 7, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"},
            ] ,
            "11-18": [
                {id: 8, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 12, state: "open"},
                {id: 9, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 35, state: "open"},
                {id: 10, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"},
            ] ,
            "11-19": [
                {id: 11, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 12, state: "open"},
                {id: 12, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 35, state: "open"},
                {id: 32, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"},
            ] ,
            "11-20": [
                {id: 41, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 12, state: "open"},
                {id: 122, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 35, state: "open"},
                {id: 412, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"}, 
            ] ,
            "11-21": [
                {id: 411, startTime: "16:00", endTime: "17:43", tickets: 40, ticketsSold: 12, state: "open"},
                {id: 52, startTime: "18:00", endTime: "19:50", tickets: 40, ticketsSold: 35, state: "open"},
                {id: 12, startTime: "20:00", endTime: "22:12", tickets: 40, ticketsSold: 3, state: "open"},
            ] ,
        });
        setSeats([
            {id: 1, row: 1, column: 1, theatreRow: 1, theatreColumn: 1, state: "available"},
            {id: 2, row: 1, column: 2, theatreRow: 1, theatreColumn: 4, state: "available"},
            {id: 3, row: 2, column: 1, theatreRow: 2, theatreColumn: 1, state: "reserved"},
            {id: 4, row: 2, column: 2, theatreRow: 2, theatreColumn: 4, state: "available"},
            {id: 5, row: 3, column: 2, theatreRow: 3, theatreColumn: 1, state: "available"},
            {id: 6, row: 3, column: 2, theatreRow: 3, theatreColumn: 4, state: "reserved"},
        ]);
        setLoading(false);
    }, []);

    if(loading) return <p>loading</p>;
    if(error) return <p>Network error</p>  

    return (
        <Box 
        sx={{display: 'flex',
            flexDirection: 'row',
            gap: 15,
        }}>
            <MovieOverview title={movie.title} image={movie.image}/>
            <BookingStepper 
            step={step} 
            movie={movie}
            handleBuyTickets={handleBuyTickets}
            handleBack={handleBack}
            theatres={theatres} 
            selectedTheatre={theatre?theatre.id:0} 
            setTheatre={handleSetTheatre}
            showtimes={showtimes}
            selectedShowtime={showtime?showtime.id:0}
            setShowtime={handleSetShowtime}
            theatreRows={theatreRows}
            theatreColumns={theatreColumns}
            seats={seats}
            selectedSeats={selectedSeats}
            setSelectedSeats={setSelectedSeats}
            />
        </Box>
    );
}

export default TicketSelector;