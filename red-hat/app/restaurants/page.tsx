'use client';

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [restaurant, setRestaurant] = useState<any>([]);
    const [mealId, setMealId] = useState<any>();
    const [restaurantId, setRestaurantId] = useState<any>();
    const [CCN, setCCN] = useState<any>();
    const [DATE, setDATE] = useState<any>();
    const [CVV, setCVV] = useState<any>();
    // async function getUser() {
    //     let username = 'faris';
    //     let password = 'faris';
    //     const base64encodedData = Buffer.from(`${username}:${password}`).toString('base64');
    //     try {
    //         const response = await fetch('http://localhost:8080/Al-Akeel/api/users', {
    //             method: 'GET',
    //             headers: {
    //                 accept: 'application/json',
    //             },
    //         });
    //         if (!response.ok) {
    //             throw new Error(`Error! status: ${response.status}`);
    //         }
    //         const result = await response.json();
    //         console.log(result);
    //         return result;
    //     } catch (err) {
    //         console.log(err);
    //     }
    // }

    // getUser().then((result) => {
    //     setRestaurant(result);
    // });


    useEffect(() => {
        fetch('http://localhost:8080/Al-Akeel/api/customer/restaurants', {
            method: 'GET',
            headers: {
                accept: '*/*',
                authorization: 'Basic ZmFyaXM6ZmFyaXM='
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setRestaurant(data);
            });
    }, []);

    const handleSubmit = (e: any) => {
        e.preventDefault();
        var mealIds = mealId.split(',').map(function (item: any) {
            return parseInt(item, 10);});
        let value1 = restaurantId;
        let value2 = mealIds;
        const data = { value1: {CCN, DATE, CVV}, value2: {value1, value2} };
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/customer/order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                authorization: 'Basic ZmFyaXM6ZmFyaXM=',
            },
            body: JSON.stringify(data),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
            }
            );
    }

    return (
        <main>
            <nav className={styles.center}>
                <Link href="/">
                    Al-Akeel | Home
                </Link>{' '}
            </nav>
            <div className={styles.main}>
                <h1 className={styles.h1}>Restaurants</h1>
                <br></br>
                <div className={styles.grid}>
                    {restaurant.map((restaurant: any) => (
                        <a
                            className={styles.card}
                            target="_self"
                            rel="noopener noreferrer"
                            // href={`/restaurants/${restaurant.id}`}
                        >
                            <h2>ID:{restaurant.id} - {restaurant.name} <span></span></h2>
                            <br></br>
                            <h3>Meals:</h3>
                            <br></br>
                            <ul>
                                {restaurant.mealsList.map((meal: any) => (
                                    <li key={meal.id}>ID:{meal.id} - {meal.name} ({meal.price})EGP</li>
                                ))}
                            </ul>
                        </a>
                    ))}
                </div>
            </div>
            <div className={styles.div}>
                <form onSubmit={handleSubmit} className={styles.form} action="/restaurants" method="post">
                    <label className={styles.label} htmlFor="restaurantId">Restaurant ID:</label>
                    <input className={styles.input} type="text" id="restaurantId" name="restaurantId" value={restaurantId} onChange={(e) => setRestaurantId(e.target.value)}/>
                    <label className={styles.label} htmlFor="mealId">Meal ID:</label>
                    <input className={styles.input} type="text" id="mealId" name="mealId" value={mealId} onChange={(e) => setMealId(e.target.value)}/>
                    <label className={styles.label} htmlFor="CCN">Credit Card Number:</label>
                    <input className={styles.input} type="text" id="CCN" name="CCN" value={CCN} onChange={(e) => setCCN(e.target.value)}/>
                    <label className={styles.label} htmlFor="DATE">Date:</label>
                    <input className={styles.input} type="text" id="DATE" name="DATE" value={DATE} onChange={(e) => setDATE(e.target.value)}/>
                    <label className={styles.label} htmlFor="CVV">CVV:</label>
                    <input className={styles.input} type="text" id="CVV" name="CVV" value={CVV} onChange={(e) => setCVV(e.target.value)}/>
                    <button className={styles.submit} type="submit">Add Meal</button>
                </form>
            </div>
        </main>
    )
}
